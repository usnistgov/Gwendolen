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


/* This line prevents findsource from adding this file to the Makefile */
#define NOT_A_DEFAULT_RCSLIB_SOURCE 1

/***************************************************************************
* File: cms_aup.cc
* Author(s): Will Shackleford, Fred Proctor
* Purpose: Provides the interface to CMS used by NML update functions including
* a CMS update function for all the basic C data types to convert NMLmsgs to XDR.
*
* NOTES:
* There are no update functions for enumerations, or pointers.
* The update function for long doubles is included, but it is recommended that
* doubles be used instead of long doubles since they will be faster and
* the increased range and precision of long doubles will be lost anyway.
****************************************************************************/

#if HAVE_CONFIG_H
#include "rcs_config.h"
#endif

#if defined(ENABLE_OLD_CMS_ASCII_UPDATER)


#include "rcs_defs.hh"		/* EXTERN_C_STD_HEADERS */


#ifdef EXTERN_C_STD_HEADERS
extern "C"
{
#endif

#include <errno.h>		/* errno global variable */
#include <limits.h>		/* SHORT_MIN, SHOR_MAX, . . . */
#include <float.h>		/* FLT_MIN, FLT_MAX */
#include <string.h>		/* memcpy(), strerror() */
#include <stdlib.h>		/* strtol(), strtoul(), strtod() */
#ifdef CenterLine		// CenterLine headers messed up the defintion of these functions
  char *strerror (int errnum);
  unsigned long strtoul (const char *, char **, int);
#endif
#include <stdio.h>
#include <ctype.h>		/* isspace() */

#ifdef EXTERN_C_STD_HEADERS
}
#endif

#include "cms.hh"		/* class CMS */
#include "cms_aup.hh"		/* class CMS_ASCII_UPDATER  */
#include "rcs_prnt.hh"		/* rcs_print_error() */
#include "dbg_mem.h"		/* DEBUG_FREE,DEBUG_MALLOC,DEBUG_CALLOC */



#define DEFAULT_WARNING_COUNT_MAX 100

/* Member functions for CMS_ASCII_UPDATER Class */
CMS_ASCII_UPDATER::CMS_ASCII_UPDATER (CMS * _cms_parent):
CMS_UPDATER (_cms_parent)
{
  /* Set pointers to NULL. */
  begin_current_string = (char *) NULL;
  end_current_string = (char *) NULL;
  max_length_current_string = 0;

  /* Store and validate constructors arguments. */
  cms_parent = _cms_parent;
  if (NULL == cms_parent)
    {
      rcs_print_error ("CMS parent for updater is NULL.\n");
      return;
    }

  /* Allocate the encoded header too large, */
  /* and find out what size it really is. */
  encoded_header =
    DEBUG_MALLOC (cms_encoded_data_explosion_factor * sizeof (CMS_HEADER));
  if (encoded_header == NULL)
    {
      rcs_print_error ("CMS:can't malloc encoded_header");
      status = CMS_CREATE_ERROR;
      return;
    }

  /* If queuing is enabled, then initialize streams for */
  /* encoding and decoding the header with the queue information. */
  if (cms_parent->queuing_enabled)
    {
      /* Allocate the encoded header too large, */
      /* and find out what size it really is. */
      encoded_queuing_header =
	DEBUG_MALLOC (neutral_size_factor * sizeof (CMS_QUEUING_HEADER));
    }
  warning_count = 0;
  warning_count_max = DEFAULT_WARNING_COUNT_MAX;
}

CMS_ASCII_UPDATER::~CMS_ASCII_UPDATER ()
{
  if (NULL != encoded_data && !using_external_encoded_data)
    {
      DEBUG_FREE (encoded_data);
      encoded_data = NULL;
    }
  if (NULL != encoded_header)
    {
      DEBUG_FREE (encoded_header);
      encoded_header = NULL;
    }
  if (NULL != encoded_queuing_header)
    {
      DEBUG_FREE (encoded_queuing_header);
      encoded_queuing_header = NULL;
    }
}


int
CMS_ASCII_UPDATER::set_mode (CMS_UPDATER_MODE _mode)
{
  CMS_UPDATER::set_mode (_mode);
  mode = _mode;
  switch (mode)
    {
    case CMS_NO_UPDATE:
      begin_current_string = end_current_string = (char *) NULL;
      max_length_current_string = 0;
      length_current_string = 0;
      break;

    case CMS_ENCODE_DATA:
      begin_current_string = end_current_string = (char *) encoded_data;
      max_length_current_string = neutral_size_factor * size;
      if (max_length_current_string > cms_parent->max_encoded_message_size)
	{
	  max_length_current_string = cms_parent->max_encoded_message_size;
	}
      length_current_string = 0;
      encoding = 1;
      break;

    case CMS_DECODE_DATA:
      begin_current_string = end_current_string = (char *) encoded_data;
      max_length_current_string = neutral_size_factor * size;
      if (max_length_current_string > cms_parent->max_encoded_message_size)
	{
	  max_length_current_string = cms_parent->max_encoded_message_size;
	}
      length_current_string = 0;
      encoding = 0;
      break;

    case CMS_ENCODE_HEADER:
      begin_current_string = end_current_string = (char *) encoded_header;
      max_length_current_string = neutral_size_factor * sizeof (CMS_HEADER);
      length_current_string = 0;
      encoding = 1;
      break;

    case CMS_DECODE_HEADER:
      begin_current_string = end_current_string = (char *) encoded_header;
      max_length_current_string = neutral_size_factor * sizeof (CMS_HEADER);
      length_current_string = 0;
      encoding = 0;
      break;

    case CMS_ENCODE_QUEUING_HEADER:
      begin_current_string = end_current_string =
	(char *) encoded_queuing_header;
      max_length_current_string =
	neutral_size_factor * sizeof (CMS_QUEUING_HEADER);
      length_current_string = 0;
      encoding = 1;
      break;

    case CMS_DECODE_QUEUING_HEADER:
      begin_current_string = end_current_string =
	(char *) encoded_queuing_header;
      max_length_current_string =
	neutral_size_factor * sizeof (CMS_QUEUING_HEADER);
      length_current_string = 0;
      encoding = 0;
      break;

    default:
      rcs_print_error ("CMS updater in invalid mode.\n");
      return (-1);
    }
  return (0);
}

int
CMS_ASCII_UPDATER::check_pointer (char RCS_HUGE * _pointer, long _bytes)
{
  if (NULL == cms_parent || NULL == begin_current_string
      || NULL == end_current_string)
    {
      rcs_print_error ("CMS_ASCII_UPDATER: Required pointer is NULL.\n");
      return (-1);
    }
  if (length_current_string + _bytes * neutral_size_factor >
      max_length_current_string)
    {
      rcs_print_error
	("CMS_ASCII_UPDATER: length of current string(%ld) + bytes to add of(%d) exceeds maximum of %ld.\n",
	 length_current_string, _bytes * neutral_size_factor,
	 max_length_current_string);
      return (-1);
    }
  return (cms_parent->check_pointer (_pointer, _bytes));
}

/* Repositions the data buffer to the very beginning */
void
CMS_ASCII_UPDATER::rewind ()
{
  CMS_UPDATER::rewind ();
  end_current_string = begin_current_string;
  length_current_string = 0;
  if (NULL != cms_parent)
    {
      cms_parent->format_size = 0;
    }
}


int
CMS_ASCII_UPDATER::get_encoded_msg_size ()
{
  return (length_current_string);
}

/* Safe strlen function. */
int
safe_strlen (char *string, int max)
{
  int retval;

  if (string == NULL)
    {
      return (-1);
    }

  retval = 0;
  while (string[retval])
    {
      if (isspace (string[retval]))
	break;
      retval++;
      if (retval >= max)
	{
	  return (-1);
	}
    }
  return retval;
}

/* Char functions */

CMS_STATUS
CMS_ASCII_UPDATER::update (char &x)
{
  /* Check to see if the pointers are in the proper range. */
  if (-1 == check_pointer ((char RCS_HUGE *) &x, sizeof (char)))
    {
      return (CMS_UPDATE_ERROR);
    }

  if (encoding)
    {
      end_current_string[0] = x;
    }
  else
    {
      x = end_current_string[0];
    }
  end_current_string += 1;
  length_current_string += 1;

  return (status);
}

CMS_STATUS
CMS_ASCII_UPDATER::update (char *x, unsigned int len)
{
  /* Check to see if the pointers are in the proper range. */
  if (-1 == check_pointer ((char RCS_HUGE *) x, sizeof (char) * len))
    {
      return (status = CMS_UPDATE_ERROR);
    }

  if (encoding)
    {
      memcpy (end_current_string, x, len);
    }
  else
    {
      memcpy (x, end_current_string, len);
    }
  end_current_string += len;
  length_current_string += len;
  return (status);
}

/* Char functions */

CMS_STATUS
CMS_ASCII_UPDATER::update (unsigned char &x)
{
  /* Check to see if the pointers are in the proper range. */
  if (-1 == check_pointer ((char RCS_HUGE *) &x, sizeof (unsigned char)))
    {
      return (status = CMS_UPDATE_ERROR);
    }

  if (encoding)
    {
      end_current_string[0] = x;
    }
  else
    {
      x = end_current_string[0];
    }
  end_current_string += 1;
  length_current_string += 1;

  return (status);
}

CMS_STATUS
CMS_ASCII_UPDATER::update (unsigned char *x, unsigned int len)
{
  /* Check to see if the pointers are in the proper range. */
  if (-1 == check_pointer ((char RCS_HUGE *) x, sizeof (unsigned char) * len))
    {
      return (status = CMS_UPDATE_ERROR);
    }

  if (encoding)
    {
      memcpy (end_current_string, x, len);
    }
  else
    {
      memcpy (x, end_current_string, len);
    }
  end_current_string += len;
  length_current_string += len;
  return (status);
}

/* Short functions */

CMS_STATUS
CMS_ASCII_UPDATER::update (short &x)
{
  /* Check to see if the pointers are in the proper range. */
  if (-1 == check_pointer ((char RCS_HUGE *) &x, sizeof (short)))
    {
      return (status = CMS_UPDATE_ERROR);
    }

  if (encoding)
    {
      end_current_string[7] = 0;
      SNPRINTF_FUNC ( SNPRINTF_ARGS(end_current_string,(beging_current_string+max_length_current_string-end_current_string)), "%-6d", x);
      if (end_current_string[7] != 0 && warning_count < warning_count_max)
	{
	  warning_count++;
	  rcs_print_error
	    ("CMS_ASCII_UPDATER: (warning) short with value %-6d caused an overflow.\n",
	     x);
	}
      end_current_string[7] = 0;
    }
  else
    {
      if (-1 == safe_strlen (end_current_string, 8))
	{
	  rcs_print_error ("CMS_ASCII_UPDATER: String is too long.\n");
	  return (status = CMS_UPDATE_ERROR);
	}
      errno = 0;
      long number = strtol (end_current_string, (char **) NULL, 10);
      if (errno != 0)
	{
	  rcs_print_error
	    ("CMS_ASCII_UPDATER: Error %ld: %s occured during strtol of(%s).\n",
	     errno, strerror (errno), end_current_string);
	  return (status = CMS_UPDATE_ERROR);
	}
      if ((number < SHRT_MIN || SHRT_MAX < number)
	  && warning_count < warning_count_max)
	{
	  warning_count++;
	  rcs_print_error
	    ("CMS_ASCII_UPDATER:  (warning) Number %d out of range for short(%d,%d)\n",
	     number, SHRT_MIN, SHRT_MAX);
	}
      x = (short) number;
    }
  end_current_string += 8;
  length_current_string += 8;

  return (status);
}

CMS_STATUS
CMS_ASCII_UPDATER::update (short *x, unsigned int len)
{
  /* Check to see if the pointers are in the proper range. */
  if (-1 == check_pointer ((char RCS_HUGE *) x, sizeof (short) * len))
    {
      return (status = CMS_UPDATE_ERROR);
    }

  for (unsigned int i = 0; i < len; i++)
    {
      if (CMS_UPDATE_ERROR == update (x[i]))
	{
	  return (status = CMS_UPDATE_ERROR);
	}
    }
  return (status);
}

CMS_STATUS
CMS_ASCII_UPDATER::update (unsigned short &x)
{
  /* Check to see if the pointers are in the proper range. */
  if (-1 == check_pointer ((char RCS_HUGE *) &x, sizeof (short)))
    {
      return (status = CMS_UPDATE_ERROR);
    }

  if (encoding)
    {
      end_current_string[7] = 0;
      SNPRINTF_FUNC ( SNPRINTF_ARGS(end_current_string,(beging_current_string+max_length_current_string-end_current_string)), "%-6d", x);
      if (end_current_string[7] != 0 && warning_count < warning_count_max)
	{
	  warning_count++;
	  rcs_print_error
	    ("CMS_ASCII_UPDATER: (warning) unsigned short with value %-6d caused an overflow.\n",
	     x);
	}
      end_current_string[7] = 0;
    }
  else
    {
      if (-1 == safe_strlen (end_current_string, 8))
	{
	  rcs_print_error ("CMS_ASCII_UPDATER: String is too long.\n");
	  return (status = CMS_UPDATE_ERROR);
	}
      errno = 0;
      unsigned long number = strtoul (end_current_string, (char **) NULL, 10);
      if (errno != 0)
	{
	  rcs_print_error
	    ("CMS_ASCII_UPDATER: Error %ld: %s occured during strtoul of (%s).\n",
	     errno, strerror (errno), end_current_string);
	  return (status = CMS_UPDATE_ERROR);
	}
      if (number > USHRT_MAX && warning_count < warning_count_max)
	{
	  warning_count++;
	  rcs_print_error
	    ("CMS_ASCII_UPDATER: Number %d out of range for unsigned short(0,%d)\n",
	     number, USHRT_MAX);
	}
      x = (unsigned short) number;
    }
  end_current_string += 8;
  length_current_string += 8;

  return (status);
}


CMS_STATUS
CMS_ASCII_UPDATER::update (unsigned short *x, unsigned int len)
{
  /* Check to see if the pointers are in the proper range. */
  if (-1 ==
      check_pointer ((char RCS_HUGE *) x, sizeof (unsigned short) * len))
    {
      return (status = CMS_UPDATE_ERROR);
    }

  for (unsigned int i = 0; i < len; i++)
    {
      if (CMS_UPDATE_ERROR == update (x[i]))
	{
	  return (status = CMS_UPDATE_ERROR);
	}
    }
  return (status);
}

/* Int  functions */

CMS_STATUS
CMS_ASCII_UPDATER::update (int &x)
{
  /* Check to see if the pointers are in the proper range. */
  if (-1 == check_pointer ((char RCS_HUGE *) &x, sizeof (int)))
    {
      return (status = CMS_UPDATE_ERROR);
    }

  if (encoding)
    {
      if (x > 9999999 && warning_count < warning_count_max)
	{
	  warning_count++;
	  rcs_print_error
	    ("CMS_ASCII_UPDATER: int %d is too large. (Use type long.)\n", x);
	}
      end_current_string[7] = 0;
      SNPRINTF_FUNC ( SNPRINTF_ARGS(end_current_string,(beging_current_string+max_length_current_string-end_current_string)), "%-6d", x);
      if (end_current_string[7] != 0 && warning_count < warning_count_max)
	{
	  warning_count++;
	  rcs_print_error
	    ("CMS_ASCII_UPDATER: (warning) int with value %-6d caused an overflow.\n",
	     x);
	}
      end_current_string[7] = 0;
    }
  else
    {
      if (-1 == safe_strlen (end_current_string, 8))
	{
	  rcs_print_error ("CMS_ASCII_UPDATER: String is too long.\n");
	  return (status = CMS_UPDATE_ERROR);
	}
      errno = 0;
      long number = strtol (end_current_string, (char **) NULL, 10);
      if (errno != 0)
	{
	  rcs_print_error
	    ("CMS_ASCII_UPDATER: Error %ld: occured during strtol of (%s).\n",
	     errno, strerror (errno), end_current_string);
	  return (status = CMS_UPDATE_ERROR);
	}
      if ((number < ((long) INT_MIN)) || (((long) INT_MAX) < number)
	  && warning_count < warning_count_max)
	{
	  warning_count++;
	  rcs_print_error
	    ("CMS_ASCII_UPDATER: (warning) Number %ld out of range for int(%ld,%ld)\n",
	     number, INT_MIN, INT_MAX);
	}
      x = (int) number;
    }
  end_current_string += 8;
  length_current_string += 8;

  return (status);
}

CMS_STATUS
CMS_ASCII_UPDATER::update (int *x, unsigned int len)
{
  /* Check to see if the pointers are in the proper range. */
  if (-1 == check_pointer ((char RCS_HUGE *) x, sizeof (int) * len))
    {
      return (status = CMS_UPDATE_ERROR);
    }

  for (unsigned int i = 0; i < len; i++)
    {
      if (CMS_UPDATE_ERROR == update (x[i]))
	{
	  return (status = CMS_UPDATE_ERROR);
	}
    }
  return (status);
}

CMS_STATUS
CMS_ASCII_UPDATER::update (unsigned int &x)
{
  /* Check to see if the pointers are in the proper range. */
  if (-1 == check_pointer ((char RCS_HUGE *) &x, sizeof (unsigned int)))
    {
      return (status = CMS_UPDATE_ERROR);
    }

  if (encoding)
    {
      if (x > 9999999 && warning_count < warning_count_max)
	{
	  warning_count++;
	  rcs_print_error
	    ("CMS_ASCII_UPDATER: unsigned int %d is too large. (Use type long.)\n",
	     x);
	}
      end_current_string[7] = 0;
      SNPRINTF_FUNC ( SNPRINTF_ARGS(end_current_string,(beging_current_string+max_length_current_string-end_current_string)), "%-6d", x);
      if (end_current_string[7] != 0 && warning_count < warning_count_max)
	{
	  warning_count++;
	  rcs_print_error
	    ("CMS_ASCII_UPDATER: (warning)unsigned int with value %-6d caused an overflow.\n",
	     x);
	}
      end_current_string[7] = 0;
    }
  else
    {
      if (-1 == safe_strlen (end_current_string, 8))
	{
	  rcs_print_error ("CMS_ASCII_UPDATER: String is too long.\n");
	  return (status = CMS_UPDATE_ERROR);
	}
      errno = 0;
      unsigned long number = strtoul (end_current_string, (char **) NULL, 10);
      if (errno != 0)
	{
	  rcs_print_error
	    ("CMS_ASCII_UPDATER: Error %ld:%s occured during strtoul of (%s).\n",
	     errno, strerror (errno), end_current_string);
	  return (status = CMS_UPDATE_ERROR);
	}
      if (UINT_MAX < number && warning_count < warning_count_max)
	{
	  rcs_print_error
	    ("CMS_ASCII_UPDATER: Number %d out of range for unsigned int (0,%d)\n",
	     number, UINT_MAX);
	}
      x = (unsigned int) number;
    }
  end_current_string += 8;
  length_current_string += 8;

  return (status);
}

CMS_STATUS
CMS_ASCII_UPDATER::update (unsigned int *x, unsigned int len)
{
  /* Check to see if the pointers are in the proper range. */
  if (-1 == check_pointer ((char RCS_HUGE *) x, sizeof (unsigned int) * len))
    {
      return (status = CMS_UPDATE_ERROR);
    }

  for (unsigned int i = 0; i < len; i++)
    {
      if (CMS_UPDATE_ERROR == update (x[i]))
	{
	  return (status = CMS_UPDATE_ERROR);
	}
    }
  return (status);
}

/* Long functions */

CMS_STATUS
CMS_ASCII_UPDATER::update (long &x)
{
  /* Check to see if the pointers are in the proper range. */
  if (-1 == check_pointer ((char RCS_HUGE *) &x, sizeof (long)))
    {
      return (status = CMS_UPDATE_ERROR);
    }

  if (encoding)
    {
      end_current_string[15] = 0;
      SNPRINTF_FUNC ( SNPRINTF_ARGS(end_current_string,(beging_current_string+max_length_current_string-end_current_string)), "%-14ld", x);
      if (end_current_string[15] != 0 && warning_count < warning_count_max)
	{
	  warning_count++;
	  rcs_print_error
	    ("CMS_ASCII_UPDATER: (warning) long with value %-14ld caused an overflow\n",
	     x);
	}
      end_current_string[15] = 0;
    }
  else
    {
      if (-1 == safe_strlen (end_current_string, 16))
	{
	  rcs_print_error ("CMS_ASCII_UPDATER: String is too long.\n");
	  return (status = CMS_UPDATE_ERROR);
	}
      errno = 0;
      long number = strtol (end_current_string, (char **) NULL, 10);
      if (errno != 0)
	{
	  rcs_print_error
	    ("CMS_ASCII_UPDATER: Error %ld occured during strtol.\n", errno);
	  return (status = CMS_UPDATE_ERROR);
	}
      x = number;
    }
  end_current_string += 16;
  length_current_string += 16;

  return (status);
}

CMS_STATUS
CMS_ASCII_UPDATER::update (long *x, unsigned int len)
{
  /* Check to see if the pointers are in the proper range. */
  if (-1 == check_pointer ((char RCS_HUGE *) x, sizeof (long) * len))
    {
      return (status = CMS_UPDATE_ERROR);
    }

  for (unsigned int i = 0; i < len; i++)
    {
      if (CMS_UPDATE_ERROR == update (x[i]))
	{
	  return (status = CMS_UPDATE_ERROR);
	}
    }
  return (status);
}

CMS_STATUS
CMS_ASCII_UPDATER::update (unsigned long &x)
{
  /* Check to see if the pointers are in the proper range. */
  if (-1 == check_pointer ((char RCS_HUGE *) &x, sizeof (unsigned long)))
    {
      return (status = CMS_UPDATE_ERROR);
    }

  if (encoding)
    {
      end_current_string[15] = 0;
      SNPRINTF_FUNC ( SNPRINTF_ARGS(end_current_string,(beging_current_string+max_length_current_string-end_current_string)), "%-14ld", x);
      if (end_current_string[15] != 0 && warning_count < warning_count_max)
	{
	  warning_count++;
	  rcs_print_error
	    ("CMS_ASCII_UPDATER: (warning) unsigned long with value %-14ld caused an overflow\n",
	     x);
	}
      end_current_string[15] = 0;
    }
  else
    {
      if (-1 == safe_strlen (end_current_string, 16))
	{
	  rcs_print_error ("CMS_ASCII_UPDATER: String is too long.\n");
	  return (status = CMS_UPDATE_ERROR);
	}
      errno = 0;
      unsigned long number = strtoul (end_current_string, (char **) NULL, 10);
      if (errno != 0)
	{
	  rcs_print_error
	    ("CMS_ASCII_UPDATER: Error %ld:%s occured during strtoul of(%s).\n",
	     errno, strerror (errno), end_current_string);
	  return (status = CMS_UPDATE_ERROR);
	}
      x = number;
    }
  end_current_string += 16;
  length_current_string += 16;

  return (status);
}

CMS_STATUS
CMS_ASCII_UPDATER::update (unsigned long *x, unsigned int len)
{
  /* Check to see if the pointers are in the proper range. */
  if (-1 == check_pointer ((char RCS_HUGE *) x, sizeof (unsigned long) * len))
    {
      return (status = CMS_UPDATE_ERROR);
    }

  for (unsigned int i = 0; i < len; i++)
    {
      if (CMS_UPDATE_ERROR == update (x[i]))
	{
	  return (status = CMS_UPDATE_ERROR);
	}
    }
  return (status);
}

/* Float functions */

CMS_STATUS
CMS_ASCII_UPDATER::update (float &x)
{
  /* Check to see if the pointers are in the proper range. */
  if (-1 == check_pointer ((char RCS_HUGE *) &x, sizeof (float)))
    {
      return (status = CMS_UPDATE_ERROR);
    }

  if (encoding)
    {
      end_current_string[15] = 0;
      SNPRINTF_FUNC ( SNPRINTF_ARGS(end_current_string,(beging_current_string+max_length_current_string-end_current_string)), "%-13.7e", x);
      if (end_current_string[15] != 0 && warning_count < warning_count_max)
	{
	  warning_count++;
	  rcs_print_error
	    ("CMS_ASCII_UPDATER: (warning) float with value %-13.7e caused an overflow\n",
	     x);
	}
      end_current_string[15] = 0;
    }
  else
    {
      if (-1 == safe_strlen (end_current_string, 16))
	{
	  rcs_print_error ("CMS_ASCII_UPDATER: String is too long.\n");
	  return (status = CMS_UPDATE_ERROR);
	}
      errno = 0;
      double number = strtod (end_current_string, (char **) NULL);
      if (errno != 0)
	{
	  rcs_print_error
	    ("CMS_ASCII_UPDATER: Error %ld occured during strtol.\n", errno);
	  return (status = CMS_UPDATE_ERROR);
	}
      if ((number < -FLT_MAX || FLT_MAX < number) &&
	  warning_count < warning_count_max)
	{
	  warning_count++;
	  rcs_print_error
	    ("CMS_ASCII_UPDATER: (warning) Number %lf out of range for float(%f,%f)\n",
	     number, -FLT_MAX, FLT_MAX);
	}
      x = (float) number;
    }
  end_current_string += 16;
  length_current_string += 16;

  return (status);
}

CMS_STATUS
CMS_ASCII_UPDATER::update (float *x, unsigned int len)
{
  /* Check to see if the pointers are in the proper range. */
  if (-1 == check_pointer ((char RCS_HUGE *) x, sizeof (float) * len))
    {
      return (status = CMS_UPDATE_ERROR);
    }

  for (unsigned int i = 0; i < len; i++)
    {
      if (CMS_UPDATE_ERROR == update (x[i]))
	{
	  return (status = CMS_UPDATE_ERROR);
	}
    }
  return (status);
}

/* Double functions */

CMS_STATUS
CMS_ASCII_UPDATER::update (double &x)
{
  /* Check to see if the pointers are in the proper range. */
  if (-1 == check_pointer ((char RCS_HUGE *) &x, sizeof (double)))
    {
      return (status = CMS_UPDATE_ERROR);
    }

  if (encoding)
    {
      end_current_string[15] = 0;
      SNPRINTF_FUNC ( SNPRINTF_ARGS(end_current_string,(beging_current_string+max_length_current_string-end_current_string)), "%-13.7e", x);
      if (end_current_string[15] != 0 && warning_count < warning_count_max)
	{
	  warning_count++;
	  rcs_print_error
	    ("CMS_ASCII_UPDATER: (warning) double with value %-13.7e caused an overflow\n",
	     x);
	}
      end_current_string[15] = 0;
    }
  else
    {
      if (-1 == safe_strlen (end_current_string, 16))
	{
	  rcs_print_error ("CMS_ASCII_UPDATER: String is too long.\n");
	  return (status = CMS_UPDATE_ERROR);
	}
      errno = 0;
      double number = strtod (end_current_string, (char **) NULL);
      if (errno != 0)
	{
	  rcs_print_error
	    ("CMS_ASCII_UPDATER: Error %ld occured during strtol.\n", errno);
	  return (status = CMS_UPDATE_ERROR);
	}
      x = number;
    }
  end_current_string += 16;
  length_current_string += 16;

  return (status);
}

CMS_STATUS
CMS_ASCII_UPDATER::update (double *x, unsigned int len)
{
  /* Check to see if the pointers are in the proper range. */
  if (-1 == check_pointer ((char RCS_HUGE *) x, sizeof (double) * len))
    {
      return (status = CMS_UPDATE_ERROR);
    }

  for (unsigned int i = 0; i < len; i++)
    {
      if (CMS_UPDATE_ERROR == update (x[i]))
	{
	  return (status = CMS_UPDATE_ERROR);
	}
    }
  return (status);
}

/* long double functions */

CMS_STATUS
CMS_ASCII_UPDATER::update (long double &x)
{
  /* Check to see if the pointers are in the proper range. */
  if (-1 == check_pointer ((char RCS_HUGE *) &x, sizeof (long double)))
    {
      return (status = CMS_UPDATE_ERROR);
    }

  if (encoding)
    {
      end_current_string[15] = 0;
      SNPRINTF_FUNC ( SNPRINTF_ARGS(end_current_string,(beging_current_string+max_length_current_string-end_current_string)), "%-13.7e", (double) x);
      if (end_current_string[15] != 0 && warning_count < warning_count_max)
	{
	  warning_count++;
	  rcs_print_error
	    ("CMS_ASCII_UPDATER: (warning) long double with value %-13.7e caused an overflow\n",
	     x);
	}
      end_current_string[15] = 0;
    }
  else
    {
      if (-1 == safe_strlen (end_current_string, 16))
	{
	  rcs_print_error ("CMS_ASCII_UPDATER: String is too long.\n");
	  return (status = CMS_UPDATE_ERROR);
	}
      errno = 0;
      double number = strtod (end_current_string, (char **) NULL);
      if (errno != 0)
	{
	  rcs_print_error
	    ("CMS_ASCII_UPDATER: Error %ld occured during strtol.\n", errno);
	  return (status = CMS_UPDATE_ERROR);
	}
      x = (long double) number;
    }
  end_current_string += 16;
  length_current_string += 16;

  return (status);
}

CMS_STATUS
CMS_ASCII_UPDATER::update (long double *x, unsigned int len)
{
  /* Check to see if the pointers are in the proper range. */
  if (-1 == check_pointer ((char RCS_HUGE *) x, sizeof (long double) * len))
    {
      return (status = CMS_UPDATE_ERROR);
    }

  for (unsigned int i = 0; i < len; i++)
    {
      if (CMS_UPDATE_ERROR == update (x[i]))
	{
	  return (status = CMS_UPDATE_ERROR);
	}
    }
  return (status);
}

//  defined(ENABLE_OLD_CMS_ASCII_UPDATER)

#else
#include "rcs_empty_source"
#endif
