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
*       New Java File starts here.
*       This file should be named PmLine.java
*/


// Set Package Name
package rcs.posemath;

// Import all NML, CMS, and RCS classes and interfaces
import rcs.nml.NMLFormatConverter;

/*
*       Class definition for PmLine
*       Automatically generated by RCS Java Diagnostics Tool.
*       on Wed Jan 07 10:53:43 EST 1998
*/
public class PmLine
{
        public PmPose start = new PmPose();
        public PmPose end = new PmPose();
        public PmCartesian uVec = new PmCartesian();

        public void update(NMLFormatConverter nml_fc)
        {
	    nml_fc.beginClass("PmLine",null);
	    nml_fc.beginClassVar("start");
	    start.update(nml_fc);
	    nml_fc.endClassVar("start");
	    nml_fc.beginClassVar("end");
	    end.update(nml_fc);
	    nml_fc.endClassVar("end");
	    nml_fc.beginClassVar("uVec");
	    uVec.update(nml_fc);
	    nml_fc.endClassVar("uVec");
	    nml_fc.endClass("PmLine",null);
        }
}
