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
*       This file should be named PmCircle.java
*/


// Set Package Name
package rcs.posemath;

// Import all NML, CMS, and RCS classes and interfaces
import rcs.nml.NMLFormatConverter;

/*
*       Class definition for PmCircle
*       Automatically generated by RCS Java Diagnostics Tool.
*       on Wed Jan 07 10:53:46 EST 1998
*/
public class PmCircle
{
        public PmCartesian center = new PmCartesian();
        public PmCartesian normal = new PmCartesian();
        public PmCartesian rTan = new PmCartesian();
        public PmCartesian rPerp = new PmCartesian();
        public PmCartesian rHelix = new PmCartesian();
        public double radius = 0;
        public double angle = 0;
        public double spiral = 0;

        public void update(NMLFormatConverter nml_fc)
        {
	    nml_fc.beginClass("PmCircle",null);
	    nml_fc.beginClassVar("center");
	    center.update(nml_fc);
	    nml_fc.endClassVar("center");
	    nml_fc.beginClassVar("normal");
	    normal.update(nml_fc);
	    nml_fc.endClassVar("normal");
	    nml_fc.beginClassVar("rTan");
	    rTan.update(nml_fc);
	    nml_fc.endClassVar("rTan");
	    nml_fc.beginClassVar("rPerp");
	    rPerp.update(nml_fc);
	    nml_fc.endClassVar("rPerp");
	    nml_fc.beginClassVar("rHelix");
	    rHelix.update(nml_fc);
	    nml_fc.endClassVar("rHelix");
	    radius = nml_fc.update_with_name("radius",radius);
	    angle = nml_fc.update_with_name("angle",angle);
	    spiral = nml_fc.update_with_name("spiral",spiral);
	    nml_fc.endClass("PmCircle",null);
        }
}
