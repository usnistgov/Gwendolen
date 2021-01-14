/* 
The NIST RCS (Real-time Control Systems) 
 library is public domain software, however it is preferred
 that the following disclaimers be attached.

Software Copywrite/Warranty Disclaimer

   This software was developed at the National Institute of Standards and
Technology by employees of the Federal Government in the course of their
official duties. Pursuant to title 17 Section 105 of the United States
Code this software is not subject to copyright protection and is in the
public domain. NIST Real-Time Control System software is an experimental
system. NIST assumes no responsibility whatsoever for its use by other
parties, and makes no guarantees, expressed or implied, about its
quality, reliability, or any other characteristic. We would appreciate
acknowledgement if the software is used. This software can be
redistributed and/or modified freely provided that any derivative works
bear some notice that they are derived from it, and any modified
versions bear some notice that they have been modified.



*/ 


/*
   _inifile.c

   C INI file reader

   Modification history:

   11-Oct-2000 WPS added code to eliminate white space after the variable.
   23-Sep-1999 WPS removed isspace which is not supported under CE.
   16-Jun-1999  FMP fixed bug in which, for example, "I" matched "INPUT"
   if "I" were not present but "INPUT" were
   18-Dec-1997  FMP set up as C/C++ split for emcnml directory
   7-Nov-1997  FMP fixed bug in afterequal so that it terminates at a 0
   24-Apr-1997  FMP split into C and C++ sections; changed ini_find to
   iniFind; put iniSection into C part
   25-Jul-1996  FMP added find_section() and ::section()
   11-Jul-1996  Fred Proctor made sure ini_find() returned NULL if a
   section were provided and no match was detected when the section
   was left; fixed bug which required the last line to have a newline
   */

#if HAVE_CONFIG_H
#include "rcs_config.h"
#include "rcs_config_include.h"
#else
#include "rcs_defs.hh"
#endif


#include "inifile.h"


#if !defined(INIFILE_USE_INET_FILES) && !defined(NO_STDIO)
#include <stdio.h>		/* FILE *, fopen(), fclose(), NULL */
#endif

#include <string.h>		/* strlen(), etc. */
#include <ctype.h>		/* isspace() */

/* if the next non-whitespace character in string is '=', returns
   ptr to next non-whitespace after that, or NULL. */
static const char *
afterequal (const char *string)
{
  const char *spot = string;	/* non-reentrant */

  while(1)
    {
      if (*spot == '=')
	{
	  /* = is there-- return next non-white, or NULL if not there */
	  for (;;)
	    {
	      spot++;
	      if (0 == *spot)
		{
		  /* ran out */
		  return NULL;
		}
	      else if (*spot != ' ' && *spot != '\t' && *spot != '\r'
		       && *spot != '\n')
		{
		  /* matched! */
		  return spot;
		}
	      else
		{
		  /* keep going for the text */
		  continue;
		}
	    }
	}
      else if (*spot == 0)
	{
	  /* end of string */
	  return NULL;
	}
      else
	{
	  /* haven't seen '=' yet-- keep going */
	  spot++;
	  continue;
	}
    }
}

/* returns ptr to first non-white in string, or NULL if it's all white */
static char *
skipwhite (char *string)
{
  for (;;)
    {
      if (*string == 0)
	{
	  return NULL;
	}

      if (*string == COMMENT_CHAR)
	{
	  return NULL;
	}

      if (*string != ' ' && *string != '\t' && *string != '\r'
	  && *string != '\n')
	{
	  return string;
	}

      string++;
    }
}

/*
   Positions file at line after section tag. Returns 0 if section found;
   -1 if not.
   */
int
findSection (void *fp, const char *section)
{
  static char line[INIFILE_MAX_LINELEN + 2];	/* 1 for newline, 1 for NULL */
  static char bracketsection[INIFILE_MAX_LINELEN + 2];
  char *nonwhite;

  /* check valid file */
  if (NULL == fp)
    {
      return -1;
    }

  /* start from beginning */
#ifndef INIFILE_USE_INET_FILES
  rewind ((FILE *) fp);
#else
  inet_file_rewind ((INET_FILE *) fp);
#endif

  /* if section is NULL, we're already positioned */
  if (NULL == section)
    {
      return 0;
    }

  /* wrap section in brackets, so it matches */
#ifndef NO_STDIO
  SNPRINTF_FUNC ( SNPRINTF_ARGS(bracketsection,sizeof(bracketsection)),
		  "[%s]", section);
#else
  strcpy (bracketsection, "[");
  strcat (bracketsection, section);
  strcat (bracketsection, "]");
#endif

  /* find [section], and position fp just after it */
#ifndef INIFILE_USE_INET_FILES
  while (!feof ((FILE *) fp))
#else
  while (!inet_file_eof ((INET_FILE *) fp))
#endif
    {

#ifndef INIFILE_USE_INET_FILES
      if (NULL == fgets (line, INIFILE_MAX_LINELEN + 1, (FILE *) fp))
	{
	  /* got to end of file without finding it */
	  return -1;
	}
#else
      if (NULL ==
	  inet_file_gets (line, INIFILE_MAX_LINELEN + 1, (INET_FILE *) fp))
	{
	  /* got to end of file without finding it */
	  return -1;
	}
#endif

      /* got a line-- check it for real data, not comment or blank line */
      if (NULL == (nonwhite = skipwhite (line)))
	{
	  /* blank line-- skip it */
	  continue;
	}

      /* not a blank line-- compare with section tag */
      if (0 != strncmp (bracketsection, nonwhite, strlen (bracketsection)))
	{
	  /* not on this line */
	  continue;
	}

      /* else it matches-- fp is now set up for search on tag */
      return 0;
    }

  /* didn't find it */
  return -1;
}

/* Returns string in file associated with tag, e.g., in a file that
   contains

   PRINTER=fred

   iniFind("PRINTER");

   would return "fred", and

   iniFind("printer");

   would return NULL.

   The FILE * needs to have been opened; its position after the call
   to iniFind is undefined.
 */

const char *
iniFind (void *fp, const char *tag, const char *section)
{
  static char line[INIFILE_MAX_LINELEN + 2];	/* 1 for newline, 1 for NULL */
  static char bracketsection[INIFILE_MAX_LINELEN + 2];
  char *nonwhite;
  int newlinepos;		/* position of newline to strip */
  int len;
  char tagend;
  char *value_string;
  char *end_value_string;
  /* check valid file */
  if (NULL == fp)
    return NULL;

  /* start from beginning */
#ifndef INIFILE_USE_INET_FILES
  rewind ((FILE *) fp);
#else
  inet_file_rewind ((INET_FILE *) fp);
#endif

  /* check for section first-- if it's non-NULL, then position file at line
     after [section] */
  if (NULL != section)
    {
#ifndef NO_STDIO
      SNPRINTF_FUNC ( SNPRINTF_ARGS(bracketsection,sizeof(bracketsection)),
		      "[%s]", section);
#else
      strcpy (bracketsection, "[");
      strcat (bracketsection, section);
      strcat (bracketsection, "]");
#endif

      /* find [section], and position fp just after it */

#ifndef INIFILE_USE_INET_FILES
      while (!feof ((FILE *) fp))
#else
      while (!inet_file_eof ((INET_FILE *) fp))
#endif
	{


#ifndef INIFILE_USE_INET_FILES
	  if (NULL == fgets (line, INIFILE_MAX_LINELEN + 1, (FILE *) fp))
	    {
	      /* got to end of file without finding it */
	      return NULL;
	    }
#else
	  if (NULL ==
	      inet_file_gets (line, INIFILE_MAX_LINELEN + 1,
			      (INET_FILE *) fp))
	    {
	      /* got to end of file without finding it */
	      return NULL;
	    }
#endif
	  /* got a line */

	  /* strip off newline */
	  newlinepos = (int) strlen (line) - 1;	/* newline is on back from 0 */
	  if (newlinepos < 0)
	    {
	      newlinepos = 0;
	    }
	  if (line[newlinepos] == '\n')
	    {
	      line[newlinepos] = 0;	/* make the newline 0 */
	    }

	  if (NULL == (nonwhite = skipwhite (line)))
	    {
	      /* blank line-- skip */
	      continue;
	    }

	  /* not a blank line, and nonwhite is first char */
	  if (0 !=
	      strncmp (bracketsection, nonwhite, strlen (bracketsection)))
	    {
	      /* not on this line */
	      continue;
	    }

	  /* it matches-- fp is now set up for search on tag */
	  break;
	}
    }

#ifndef INIFILE_USE_INET_FILES
  while (!feof ((FILE *) fp))
#else
  while (!inet_file_eof ((INET_FILE *) fp))
#endif
    {
      /* check for end of file */
#ifndef INIFILE_USE_INET_FILES
      if (NULL == fgets (line, INIFILE_MAX_LINELEN + 1, (FILE *) fp))
	{
	  /* got to end of file without finding it */
	  return NULL;
	}
#else
      if (NULL ==
	  inet_file_gets (line, INIFILE_MAX_LINELEN + 1, (INET_FILE *) fp))
	{
	  /* got to end of file without finding it */
	  return NULL;
	}
#endif

      /* got a line */

      /* strip off newline */
      newlinepos = (int) strlen (line) - 1;	/* newline is on back from 0 */
      if (newlinepos < 0)
	{
	  newlinepos = 0;
	}
      if (line[newlinepos] == '\n')
	{
	  line[newlinepos] = 0;	/* make the newline 0 */
	}

      /* skip leading whitespace */
      if (NULL == (nonwhite = skipwhite (line)))
	{
	  /* blank line-- skip */
	  continue;
	}

      /* check for '[' char-- if so, it's a section tag, and we're out of
         our section */
      if (NULL != section && nonwhite[0] == '[')
	{
	  return NULL;
	}

      len = (int) strlen (tag);

      if (0 != strncmp (tag, nonwhite, len))
	{
	  /* not on this line */
	  continue;
	}

      /* it matches the first part of the string-- if whitespace or =
         is next char then call it a match */
      tagend = nonwhite[len];
      if (tagend == ' ' || tagend == '\r' || tagend == '\t' || tagend == '\n'
	  || tagend == '=')
	{
	  /* it matches-- return string after =, or NULL */
	  nonwhite += len;
	  value_string = (char *) afterequal (nonwhite);	/* Cast is needed because we are discarding the const. */
	  /* Eliminate white space at the end of a line also. */
	  if (NULL == value_string)
	    {
	      value_string = (char *) skipwhite(nonwhite);
	    }
	  if(NULL == value_string)
	    {
	      return NULL;
	    }
	  end_value_string = value_string + strlen (value_string) - 1;
	  while (*end_value_string == ' ' || *end_value_string == '\t'
		 || *end_value_string == '\r')
	    {
	      *end_value_string = 0;
	      end_value_string--;
	    }
	  return value_string;
	}
      /* else continue */
    }

  return NULL;
}

/*
   given 'section' and array of strings, fills strings with what was
   found in the section, one line per string. Comments and blank lines
   are omitted. 'array' is assumed to be allocated, of 'max' entries
   of size INIFILE_MAX_LINELEN.

   Returns number of entries found; 0 if section is there but no entries
   in it, or -1 if section is not there.
*/

int
iniSection (void *fp, const char *section, INIFILE_ENTRY array[], int max)
{
  static char line[INIFILE_MAX_LINELEN + 2];	/* 1 for newline, 1 for NULL */
  int count = 0;
  char *nonwhite;
  int newlinepos;
  const char *entry;

  if (NULL == fp)
    {
      return -1;
    }

  /*  position at section */
  if (-1 == findSection (fp, section))
    {
      /* didn't find it */
      return -1;
    }

  /* found section-- start loading lines */

#ifndef INIFILE_USE_INET_FILES
  while (!feof ((FILE *) fp) &&
#else
  while (!inet_file_eof ((INET_FILE *) fp) &&
#endif
	 count < max)
    {
#ifndef INIFILE_USE_INET_FILES
      if (NULL == fgets (line, INIFILE_MAX_LINELEN + 1, (FILE *) fp))
	{
	  /* got to end of file without finding it */
	  return count;
	}
#else
      if (NULL ==
	  inet_file_gets (line, INIFILE_MAX_LINELEN + 1, (INET_FILE *) fp))
	{
	  /* got to end of file without finding it */
	  return count;
	}
#endif

      /* got a line-- check for blank */
      if (NULL == (nonwhite = skipwhite (line)))
	{
	  continue;
	}

      /* check for new section-- if so, we're done */
      if ('[' == *nonwhite)
	{
	  return count;
	}

      /* strip off newline  */
      newlinepos = (int) strlen (line) - 1;	/* newline is one back from 0 */
      if (newlinepos < 0)
	{
	  newlinepos = 0;
	}
      if (line[newlinepos] == '\n')
	{
	  line[newlinepos] = 0;	/* make the newline 0 */
	}

      /* it's a valid line-- copy into entry struct */

      /* read first tag */
      sscanf (line, "%s", array[count].tag);

      /* copy everything after equal to the rest */

      entry = afterequal (line);
      if (NULL != entry)
	{
	  strcpy (array[count].rest, afterequal (line));
	}
      else
	{
	  array[count].rest[0] = 0;	/* make it the empty string */
	}
      count++;
    }

  /* got to end of file */
  return count;
}
