--
--      New Ada Body File starts here.
--      This file should be named posemath_n_ada.adb
--      Automatically generated by NML CodeGen Java Applet.
--      on Wed Aug 25 13:07:04 EDT 2004
--

with Nml_Msg; use Nml_Msg;

with Cms;


--      Some standard Ada Packages we always need.
with Unchecked_Deallocation;
with Unchecked_Conversion;
with Interfaces.C; use Interfaces.C;
with Interfaces.C.Strings; use Interfaces.C.Strings;

package body posemath_n_ada is

        -- Create some common variables  and functions needed for updating Enumeration types.
        Enum_PM_AXIS_Name_List : constant Char_Array(1..20) := (
                'P','M','_','X',nul,
                'P','M','_','Y',nul,
                'P','M','_','Z',nul,
                nul,nul,nul,nul,nul
                );
        Enum_PM_AXIS_Int_List : constant Cms.Int_Array(1..4) := (
                0, -- PM_X
                1, -- PM_Y
                2, -- PM_Z
                -1
                );
        enum_PM_AXIS_PM_X_Key_Name : constant Interfaces.C.Strings.Chars_Ptr := Interfaces.C.Strings.New_String("PM_X");
        enum_PM_AXIS_PM_Y_Key_Name : constant Interfaces.C.Strings.Chars_Ptr := Interfaces.C.Strings.New_String("PM_Y");
        enum_PM_AXIS_PM_Z_Key_Name : constant Interfaces.C.Strings.Chars_Ptr := Interfaces.C.Strings.New_String("PM_Z");
        function Enum_PM_AXIS_Symbol_Lookup(enum_int : in long) return Interfaces.C.Strings.chars_ptr;
        pragma Export(C,Enum_PM_AXIS_Symbol_Lookup,"ada_PM_AXIS_posemath_n_ada_symbol_lookup");

        function Enum_PM_AXIS_Symbol_Lookup(enum_int: in long) return Interfaces.C.Strings.chars_ptr is
        begin
                case enum_int is
                        when 0  =>      return enum_PM_AXIS_PM_X_Key_Name; -- PM_X
                        when 1  =>      return enum_PM_AXIS_PM_Y_Key_Name; -- PM_Y
                        when 2  =>      return enum_PM_AXIS_PM_Z_Key_Name; -- PM_Z
                        when others     =>      return Null_Ptr;
                end case;
        end Enum_PM_AXIS_Symbol_Lookup;

        function Enum_PM_AXIS_To_Int(enum_val: in PM_AXIS) return int is
        begin
                case enum_val is
                        when PM_Z       =>      return 2;
                        when PM_Y       =>      return 1;
                        when PM_X       =>      return 0;
                        when Bad_PM_AXIS_Value  =>      return -1;
                end case;
        end Enum_PM_AXIS_To_Int;

        function Int_To_Enum_PM_AXIS(enum_int: in int) return PM_AXIS is
        begin
                case enum_int is
                        when 2  =>      return PM_Z;
                        when 1  =>      return PM_Y;
                        when 0  =>      return PM_X;
                        when others     =>      return Bad_PM_AXIS_Value;
                end case;
        end Int_To_Enum_PM_AXIS;

        Enum_PM_AXIS_Info : constant Cms.Cms_Enum_Info_Access := Cms.New_Cms_Enum_Info(
                        "PM_AXIS",
                        Enum_PM_AXIS_Name_List,
                        Enum_PM_AXIS_Int_List,
                        5,
                        3,
                        Enum_PM_AXIS_Symbol_Lookup'Access);


        -- Every NMLmsg type needs an update and an initialize function.

        procedure Update_PM_CARTESIAN(Cms_Ptr : in Cms.Cms_Access; Msg : in PM_CARTESIAN_Access) is
        begin
                Cms.Begin_Class(Cms_Ptr,"PM_CARTESIAN","");
                Cms.Update_Double(Cms_Ptr, "x", Msg.x);
                Cms.Update_Double(Cms_Ptr, "y", Msg.y);
                Cms.Update_Double(Cms_Ptr, "z", Msg.z);
                Cms.End_Class(Cms_Ptr,"PM_CARTESIAN","");
        end Update_PM_CARTESIAN;

        procedure Update_Internal_PM_CARTESIAN(Cms_Ptr : in Cms.Cms_Access; Msg : in out PM_CARTESIAN) is
        begin
                Cms.Begin_Class(Cms_Ptr,"PM_CARTESIAN","");
                Cms.Update_Double(Cms_Ptr, "x", Msg.x);
                Cms.Update_Double(Cms_Ptr, "y", Msg.y);
                Cms.Update_Double(Cms_Ptr, "z", Msg.z);
                Cms.End_Class(Cms_Ptr,"PM_CARTESIAN","");
        end Update_Internal_PM_CARTESIAN;


        procedure Update_PM_CYLINDRICAL(Cms_Ptr : in Cms.Cms_Access; Msg : in PM_CYLINDRICAL_Access) is
        begin
                Cms.Begin_Class(Cms_Ptr,"PM_CYLINDRICAL","");
                Cms.Update_Double(Cms_Ptr, "theta", Msg.theta);
                Cms.Update_Double(Cms_Ptr, "r", Msg.r);
                Cms.Update_Double(Cms_Ptr, "z", Msg.z);
                Cms.End_Class(Cms_Ptr,"PM_CYLINDRICAL","");
        end Update_PM_CYLINDRICAL;

        procedure Update_Internal_PM_CYLINDRICAL(Cms_Ptr : in Cms.Cms_Access; Msg : in out PM_CYLINDRICAL) is
        begin
                Cms.Begin_Class(Cms_Ptr,"PM_CYLINDRICAL","");
                Cms.Update_Double(Cms_Ptr, "theta", Msg.theta);
                Cms.Update_Double(Cms_Ptr, "r", Msg.r);
                Cms.Update_Double(Cms_Ptr, "z", Msg.z);
                Cms.End_Class(Cms_Ptr,"PM_CYLINDRICAL","");
        end Update_Internal_PM_CYLINDRICAL;


        procedure Update_PM_EULER_ZYX(Cms_Ptr : in Cms.Cms_Access; Msg : in PM_EULER_ZYX_Access) is
        begin
                Cms.Begin_Class(Cms_Ptr,"PM_EULER_ZYX","");
                Cms.Update_Double(Cms_Ptr, "z", Msg.z);
                Cms.Update_Double(Cms_Ptr, "y", Msg.y);
                Cms.Update_Double(Cms_Ptr, "x", Msg.x);
                Cms.End_Class(Cms_Ptr,"PM_EULER_ZYX","");
        end Update_PM_EULER_ZYX;

        procedure Update_Internal_PM_EULER_ZYX(Cms_Ptr : in Cms.Cms_Access; Msg : in out PM_EULER_ZYX) is
        begin
                Cms.Begin_Class(Cms_Ptr,"PM_EULER_ZYX","");
                Cms.Update_Double(Cms_Ptr, "z", Msg.z);
                Cms.Update_Double(Cms_Ptr, "y", Msg.y);
                Cms.Update_Double(Cms_Ptr, "x", Msg.x);
                Cms.End_Class(Cms_Ptr,"PM_EULER_ZYX","");
        end Update_Internal_PM_EULER_ZYX;


        procedure Update_PM_EULER_ZYZ(Cms_Ptr : in Cms.Cms_Access; Msg : in PM_EULER_ZYZ_Access) is
        begin
                Cms.Begin_Class(Cms_Ptr,"PM_EULER_ZYZ","");
                Cms.Update_Double(Cms_Ptr, "z", Msg.z);
                Cms.Update_Double(Cms_Ptr, "y", Msg.y);
                Cms.Update_Double(Cms_Ptr, "zp", Msg.zp);
                Cms.End_Class(Cms_Ptr,"PM_EULER_ZYZ","");
        end Update_PM_EULER_ZYZ;

        procedure Update_Internal_PM_EULER_ZYZ(Cms_Ptr : in Cms.Cms_Access; Msg : in out PM_EULER_ZYZ) is
        begin
                Cms.Begin_Class(Cms_Ptr,"PM_EULER_ZYZ","");
                Cms.Update_Double(Cms_Ptr, "z", Msg.z);
                Cms.Update_Double(Cms_Ptr, "y", Msg.y);
                Cms.Update_Double(Cms_Ptr, "zp", Msg.zp);
                Cms.End_Class(Cms_Ptr,"PM_EULER_ZYZ","");
        end Update_Internal_PM_EULER_ZYZ;


        procedure Update_PM_QUATERNION(Cms_Ptr : in Cms.Cms_Access; Msg : in PM_QUATERNION_Access) is
        begin
                Cms.Begin_Class(Cms_Ptr,"PM_QUATERNION","");
                Cms.Update_Double(Cms_Ptr, "s", Msg.s);
                Cms.Update_Double(Cms_Ptr, "x", Msg.x);
                Cms.Update_Double(Cms_Ptr, "y", Msg.y);
                Cms.Update_Double(Cms_Ptr, "z", Msg.z);
                Cms.End_Class(Cms_Ptr,"PM_QUATERNION","");
        end Update_PM_QUATERNION;

        procedure Update_Internal_PM_QUATERNION(Cms_Ptr : in Cms.Cms_Access; Msg : in out PM_QUATERNION) is
        begin
                Cms.Begin_Class(Cms_Ptr,"PM_QUATERNION","");
                Cms.Update_Double(Cms_Ptr, "s", Msg.s);
                Cms.Update_Double(Cms_Ptr, "x", Msg.x);
                Cms.Update_Double(Cms_Ptr, "y", Msg.y);
                Cms.Update_Double(Cms_Ptr, "z", Msg.z);
                Cms.End_Class(Cms_Ptr,"PM_QUATERNION","");
        end Update_Internal_PM_QUATERNION;


        procedure Update_PM_ROTATION_VECTOR(Cms_Ptr : in Cms.Cms_Access; Msg : in PM_ROTATION_VECTOR_Access) is
        begin
                Cms.Begin_Class(Cms_Ptr,"PM_ROTATION_VECTOR","");
                Cms.Update_Double(Cms_Ptr, "s", Msg.s);
                Cms.Update_Double(Cms_Ptr, "x", Msg.x);
                Cms.Update_Double(Cms_Ptr, "y", Msg.y);
                Cms.Update_Double(Cms_Ptr, "z", Msg.z);
                Cms.End_Class(Cms_Ptr,"PM_ROTATION_VECTOR","");
        end Update_PM_ROTATION_VECTOR;

        procedure Update_Internal_PM_ROTATION_VECTOR(Cms_Ptr : in Cms.Cms_Access; Msg : in out PM_ROTATION_VECTOR) is
        begin
                Cms.Begin_Class(Cms_Ptr,"PM_ROTATION_VECTOR","");
                Cms.Update_Double(Cms_Ptr, "s", Msg.s);
                Cms.Update_Double(Cms_Ptr, "x", Msg.x);
                Cms.Update_Double(Cms_Ptr, "y", Msg.y);
                Cms.Update_Double(Cms_Ptr, "z", Msg.z);
                Cms.End_Class(Cms_Ptr,"PM_ROTATION_VECTOR","");
        end Update_Internal_PM_ROTATION_VECTOR;


        procedure Update_PM_RPY(Cms_Ptr : in Cms.Cms_Access; Msg : in PM_RPY_Access) is
        begin
                Cms.Begin_Class(Cms_Ptr,"PM_RPY","");
                Cms.Update_Double(Cms_Ptr, "r", Msg.r);
                Cms.Update_Double(Cms_Ptr, "p", Msg.p);
                Cms.Update_Double(Cms_Ptr, "y", Msg.y);
                Cms.End_Class(Cms_Ptr,"PM_RPY","");
        end Update_PM_RPY;

        procedure Update_Internal_PM_RPY(Cms_Ptr : in Cms.Cms_Access; Msg : in out PM_RPY) is
        begin
                Cms.Begin_Class(Cms_Ptr,"PM_RPY","");
                Cms.Update_Double(Cms_Ptr, "r", Msg.r);
                Cms.Update_Double(Cms_Ptr, "p", Msg.p);
                Cms.Update_Double(Cms_Ptr, "y", Msg.y);
                Cms.End_Class(Cms_Ptr,"PM_RPY","");
        end Update_Internal_PM_RPY;


        procedure Update_PM_SPHERICAL(Cms_Ptr : in Cms.Cms_Access; Msg : in PM_SPHERICAL_Access) is
        begin
                Cms.Begin_Class(Cms_Ptr,"PM_SPHERICAL","");
                Cms.Update_Double(Cms_Ptr, "theta", Msg.theta);
                Cms.Update_Double(Cms_Ptr, "phi", Msg.phi);
                Cms.Update_Double(Cms_Ptr, "r", Msg.r);
                Cms.End_Class(Cms_Ptr,"PM_SPHERICAL","");
        end Update_PM_SPHERICAL;

        procedure Update_Internal_PM_SPHERICAL(Cms_Ptr : in Cms.Cms_Access; Msg : in out PM_SPHERICAL) is
        begin
                Cms.Begin_Class(Cms_Ptr,"PM_SPHERICAL","");
                Cms.Update_Double(Cms_Ptr, "theta", Msg.theta);
                Cms.Update_Double(Cms_Ptr, "phi", Msg.phi);
                Cms.Update_Double(Cms_Ptr, "r", Msg.r);
                Cms.End_Class(Cms_Ptr,"PM_SPHERICAL","");
        end Update_Internal_PM_SPHERICAL;


        procedure Update_PmCartesian(Cms_Ptr : in Cms.Cms_Access; Msg : in PmCartesian_Access) is
        begin
                Cms.Begin_Class(Cms_Ptr,"PmCartesian","");
                Cms.Update_Double(Cms_Ptr, "x", Msg.x);
                Cms.Update_Double(Cms_Ptr, "y", Msg.y);
                Cms.Update_Double(Cms_Ptr, "z", Msg.z);
                Cms.End_Class(Cms_Ptr,"PmCartesian","");
        end Update_PmCartesian;

        procedure Update_Internal_PmCartesian(Cms_Ptr : in Cms.Cms_Access; Msg : in out PmCartesian) is
        begin
                Cms.Begin_Class(Cms_Ptr,"PmCartesian","");
                Cms.Update_Double(Cms_Ptr, "x", Msg.x);
                Cms.Update_Double(Cms_Ptr, "y", Msg.y);
                Cms.Update_Double(Cms_Ptr, "z", Msg.z);
                Cms.End_Class(Cms_Ptr,"PmCartesian","");
        end Update_Internal_PmCartesian;


        procedure Update_PmCylindrical(Cms_Ptr : in Cms.Cms_Access; Msg : in PmCylindrical_Access) is
        begin
                Cms.Begin_Class(Cms_Ptr,"PmCylindrical","");
                Cms.Update_Double(Cms_Ptr, "theta", Msg.theta);
                Cms.Update_Double(Cms_Ptr, "r", Msg.r);
                Cms.Update_Double(Cms_Ptr, "z", Msg.z);
                Cms.End_Class(Cms_Ptr,"PmCylindrical","");
        end Update_PmCylindrical;

        procedure Update_Internal_PmCylindrical(Cms_Ptr : in Cms.Cms_Access; Msg : in out PmCylindrical) is
        begin
                Cms.Begin_Class(Cms_Ptr,"PmCylindrical","");
                Cms.Update_Double(Cms_Ptr, "theta", Msg.theta);
                Cms.Update_Double(Cms_Ptr, "r", Msg.r);
                Cms.Update_Double(Cms_Ptr, "z", Msg.z);
                Cms.End_Class(Cms_Ptr,"PmCylindrical","");
        end Update_Internal_PmCylindrical;


        procedure Update_PmEulerZyx(Cms_Ptr : in Cms.Cms_Access; Msg : in PmEulerZyx_Access) is
        begin
                Cms.Begin_Class(Cms_Ptr,"PmEulerZyx","");
                Cms.Update_Double(Cms_Ptr, "z", Msg.z);
                Cms.Update_Double(Cms_Ptr, "y", Msg.y);
                Cms.Update_Double(Cms_Ptr, "x", Msg.x);
                Cms.End_Class(Cms_Ptr,"PmEulerZyx","");
        end Update_PmEulerZyx;

        procedure Update_Internal_PmEulerZyx(Cms_Ptr : in Cms.Cms_Access; Msg : in out PmEulerZyx) is
        begin
                Cms.Begin_Class(Cms_Ptr,"PmEulerZyx","");
                Cms.Update_Double(Cms_Ptr, "z", Msg.z);
                Cms.Update_Double(Cms_Ptr, "y", Msg.y);
                Cms.Update_Double(Cms_Ptr, "x", Msg.x);
                Cms.End_Class(Cms_Ptr,"PmEulerZyx","");
        end Update_Internal_PmEulerZyx;


        procedure Update_PmEulerZyz(Cms_Ptr : in Cms.Cms_Access; Msg : in PmEulerZyz_Access) is
        begin
                Cms.Begin_Class(Cms_Ptr,"PmEulerZyz","");
                Cms.Update_Double(Cms_Ptr, "z", Msg.z);
                Cms.Update_Double(Cms_Ptr, "y", Msg.y);
                Cms.Update_Double(Cms_Ptr, "zp", Msg.zp);
                Cms.End_Class(Cms_Ptr,"PmEulerZyz","");
        end Update_PmEulerZyz;

        procedure Update_Internal_PmEulerZyz(Cms_Ptr : in Cms.Cms_Access; Msg : in out PmEulerZyz) is
        begin
                Cms.Begin_Class(Cms_Ptr,"PmEulerZyz","");
                Cms.Update_Double(Cms_Ptr, "z", Msg.z);
                Cms.Update_Double(Cms_Ptr, "y", Msg.y);
                Cms.Update_Double(Cms_Ptr, "zp", Msg.zp);
                Cms.End_Class(Cms_Ptr,"PmEulerZyz","");
        end Update_Internal_PmEulerZyz;


        procedure Update_PmQuaternion(Cms_Ptr : in Cms.Cms_Access; Msg : in PmQuaternion_Access) is
        begin
                Cms.Begin_Class(Cms_Ptr,"PmQuaternion","");
                Cms.Update_Double(Cms_Ptr, "s", Msg.s);
                Cms.Update_Double(Cms_Ptr, "x", Msg.x);
                Cms.Update_Double(Cms_Ptr, "y", Msg.y);
                Cms.Update_Double(Cms_Ptr, "z", Msg.z);
                Cms.End_Class(Cms_Ptr,"PmQuaternion","");
        end Update_PmQuaternion;

        procedure Update_Internal_PmQuaternion(Cms_Ptr : in Cms.Cms_Access; Msg : in out PmQuaternion) is
        begin
                Cms.Begin_Class(Cms_Ptr,"PmQuaternion","");
                Cms.Update_Double(Cms_Ptr, "s", Msg.s);
                Cms.Update_Double(Cms_Ptr, "x", Msg.x);
                Cms.Update_Double(Cms_Ptr, "y", Msg.y);
                Cms.Update_Double(Cms_Ptr, "z", Msg.z);
                Cms.End_Class(Cms_Ptr,"PmQuaternion","");
        end Update_Internal_PmQuaternion;


        procedure Update_PmRotationVector(Cms_Ptr : in Cms.Cms_Access; Msg : in PmRotationVector_Access) is
        begin
                Cms.Begin_Class(Cms_Ptr,"PmRotationVector","");
                Cms.Update_Double(Cms_Ptr, "s", Msg.s);
                Cms.Update_Double(Cms_Ptr, "x", Msg.x);
                Cms.Update_Double(Cms_Ptr, "y", Msg.y);
                Cms.Update_Double(Cms_Ptr, "z", Msg.z);
                Cms.End_Class(Cms_Ptr,"PmRotationVector","");
        end Update_PmRotationVector;

        procedure Update_Internal_PmRotationVector(Cms_Ptr : in Cms.Cms_Access; Msg : in out PmRotationVector) is
        begin
                Cms.Begin_Class(Cms_Ptr,"PmRotationVector","");
                Cms.Update_Double(Cms_Ptr, "s", Msg.s);
                Cms.Update_Double(Cms_Ptr, "x", Msg.x);
                Cms.Update_Double(Cms_Ptr, "y", Msg.y);
                Cms.Update_Double(Cms_Ptr, "z", Msg.z);
                Cms.End_Class(Cms_Ptr,"PmRotationVector","");
        end Update_Internal_PmRotationVector;


        procedure Update_PmRpy(Cms_Ptr : in Cms.Cms_Access; Msg : in PmRpy_Access) is
        begin
                Cms.Begin_Class(Cms_Ptr,"PmRpy","");
                Cms.Update_Double(Cms_Ptr, "r", Msg.r);
                Cms.Update_Double(Cms_Ptr, "p", Msg.p);
                Cms.Update_Double(Cms_Ptr, "y", Msg.y);
                Cms.End_Class(Cms_Ptr,"PmRpy","");
        end Update_PmRpy;

        procedure Update_Internal_PmRpy(Cms_Ptr : in Cms.Cms_Access; Msg : in out PmRpy) is
        begin
                Cms.Begin_Class(Cms_Ptr,"PmRpy","");
                Cms.Update_Double(Cms_Ptr, "r", Msg.r);
                Cms.Update_Double(Cms_Ptr, "p", Msg.p);
                Cms.Update_Double(Cms_Ptr, "y", Msg.y);
                Cms.End_Class(Cms_Ptr,"PmRpy","");
        end Update_Internal_PmRpy;


        procedure Update_PmSpherical(Cms_Ptr : in Cms.Cms_Access; Msg : in PmSpherical_Access) is
        begin
                Cms.Begin_Class(Cms_Ptr,"PmSpherical","");
                Cms.Update_Double(Cms_Ptr, "theta", Msg.theta);
                Cms.Update_Double(Cms_Ptr, "phi", Msg.phi);
                Cms.Update_Double(Cms_Ptr, "r", Msg.r);
                Cms.End_Class(Cms_Ptr,"PmSpherical","");
        end Update_PmSpherical;

        procedure Update_Internal_PmSpherical(Cms_Ptr : in Cms.Cms_Access; Msg : in out PmSpherical) is
        begin
                Cms.Begin_Class(Cms_Ptr,"PmSpherical","");
                Cms.Update_Double(Cms_Ptr, "theta", Msg.theta);
                Cms.Update_Double(Cms_Ptr, "phi", Msg.phi);
                Cms.Update_Double(Cms_Ptr, "r", Msg.r);
                Cms.End_Class(Cms_Ptr,"PmSpherical","");
        end Update_Internal_PmSpherical;


        procedure Update_PM_CIRCLE(Cms_Ptr : in Cms.Cms_Access; Msg : in PM_CIRCLE_Access) is
        begin
                Cms.Begin_Class(Cms_Ptr,"PM_CIRCLE","");
                Cms.Begin_Class_Var(Cms_Ptr,"center");
                Update_Internal_PM_CARTESIAN(Cms_Ptr,Msg.center);
                Cms.End_Class_Var(Cms_Ptr,"center");
                Cms.Begin_Class_Var(Cms_Ptr,"normal");
                Update_Internal_PM_CARTESIAN(Cms_Ptr,Msg.normal);
                Cms.End_Class_Var(Cms_Ptr,"normal");
                Cms.Begin_Class_Var(Cms_Ptr,"rTan");
                Update_Internal_PM_CARTESIAN(Cms_Ptr,Msg.rTan);
                Cms.End_Class_Var(Cms_Ptr,"rTan");
                Cms.Begin_Class_Var(Cms_Ptr,"rPerp");
                Update_Internal_PM_CARTESIAN(Cms_Ptr,Msg.rPerp);
                Cms.End_Class_Var(Cms_Ptr,"rPerp");
                Cms.Begin_Class_Var(Cms_Ptr,"rHelix");
                Update_Internal_PM_CARTESIAN(Cms_Ptr,Msg.rHelix);
                Cms.End_Class_Var(Cms_Ptr,"rHelix");
                Cms.Update_Double(Cms_Ptr, "radius", Msg.radius);
                Cms.Update_Double(Cms_Ptr, "angle", Msg.angle);
                Cms.Update_Double(Cms_Ptr, "spiral", Msg.spiral);
                Cms.End_Class(Cms_Ptr,"PM_CIRCLE","");
        end Update_PM_CIRCLE;

        procedure Update_Internal_PM_CIRCLE(Cms_Ptr : in Cms.Cms_Access; Msg : in out PM_CIRCLE) is
        begin
                Cms.Begin_Class(Cms_Ptr,"PM_CIRCLE","");
                Cms.Begin_Class_Var(Cms_Ptr,"center");
                Update_Internal_PM_CARTESIAN(Cms_Ptr,Msg.center);
                Cms.End_Class_Var(Cms_Ptr,"center");
                Cms.Begin_Class_Var(Cms_Ptr,"normal");
                Update_Internal_PM_CARTESIAN(Cms_Ptr,Msg.normal);
                Cms.End_Class_Var(Cms_Ptr,"normal");
                Cms.Begin_Class_Var(Cms_Ptr,"rTan");
                Update_Internal_PM_CARTESIAN(Cms_Ptr,Msg.rTan);
                Cms.End_Class_Var(Cms_Ptr,"rTan");
                Cms.Begin_Class_Var(Cms_Ptr,"rPerp");
                Update_Internal_PM_CARTESIAN(Cms_Ptr,Msg.rPerp);
                Cms.End_Class_Var(Cms_Ptr,"rPerp");
                Cms.Begin_Class_Var(Cms_Ptr,"rHelix");
                Update_Internal_PM_CARTESIAN(Cms_Ptr,Msg.rHelix);
                Cms.End_Class_Var(Cms_Ptr,"rHelix");
                Cms.Update_Double(Cms_Ptr, "radius", Msg.radius);
                Cms.Update_Double(Cms_Ptr, "angle", Msg.angle);
                Cms.Update_Double(Cms_Ptr, "spiral", Msg.spiral);
                Cms.End_Class(Cms_Ptr,"PM_CIRCLE","");
        end Update_Internal_PM_CIRCLE;


        procedure Update_PM_POSE(Cms_Ptr : in Cms.Cms_Access; Msg : in PM_POSE_Access) is
        begin
                Cms.Begin_Class(Cms_Ptr,"PM_POSE","");
                Cms.Begin_Class_Var(Cms_Ptr,"tran");
                Update_Internal_PM_CARTESIAN(Cms_Ptr,Msg.tran);
                Cms.End_Class_Var(Cms_Ptr,"tran");
                Cms.Begin_Class_Var(Cms_Ptr,"rot");
                Update_Internal_PM_QUATERNION(Cms_Ptr,Msg.rot);
                Cms.End_Class_Var(Cms_Ptr,"rot");
                Cms.End_Class(Cms_Ptr,"PM_POSE","");
        end Update_PM_POSE;

        procedure Update_Internal_PM_POSE(Cms_Ptr : in Cms.Cms_Access; Msg : in out PM_POSE) is
        begin
                Cms.Begin_Class(Cms_Ptr,"PM_POSE","");
                Cms.Begin_Class_Var(Cms_Ptr,"tran");
                Update_Internal_PM_CARTESIAN(Cms_Ptr,Msg.tran);
                Cms.End_Class_Var(Cms_Ptr,"tran");
                Cms.Begin_Class_Var(Cms_Ptr,"rot");
                Update_Internal_PM_QUATERNION(Cms_Ptr,Msg.rot);
                Cms.End_Class_Var(Cms_Ptr,"rot");
                Cms.End_Class(Cms_Ptr,"PM_POSE","");
        end Update_Internal_PM_POSE;


        procedure Update_PM_ROTATION_MATRIX(Cms_Ptr : in Cms.Cms_Access; Msg : in PM_ROTATION_MATRIX_Access) is
        begin
                Cms.Begin_Class(Cms_Ptr,"PM_ROTATION_MATRIX","");
                Cms.Begin_Class_Var(Cms_Ptr,"x");
                Update_Internal_PM_CARTESIAN(Cms_Ptr,Msg.x);
                Cms.End_Class_Var(Cms_Ptr,"x");
                Cms.Begin_Class_Var(Cms_Ptr,"y");
                Update_Internal_PM_CARTESIAN(Cms_Ptr,Msg.y);
                Cms.End_Class_Var(Cms_Ptr,"y");
                Cms.Begin_Class_Var(Cms_Ptr,"z");
                Update_Internal_PM_CARTESIAN(Cms_Ptr,Msg.z);
                Cms.End_Class_Var(Cms_Ptr,"z");
                Cms.End_Class(Cms_Ptr,"PM_ROTATION_MATRIX","");
        end Update_PM_ROTATION_MATRIX;

        procedure Update_Internal_PM_ROTATION_MATRIX(Cms_Ptr : in Cms.Cms_Access; Msg : in out PM_ROTATION_MATRIX) is
        begin
                Cms.Begin_Class(Cms_Ptr,"PM_ROTATION_MATRIX","");
                Cms.Begin_Class_Var(Cms_Ptr,"x");
                Update_Internal_PM_CARTESIAN(Cms_Ptr,Msg.x);
                Cms.End_Class_Var(Cms_Ptr,"x");
                Cms.Begin_Class_Var(Cms_Ptr,"y");
                Update_Internal_PM_CARTESIAN(Cms_Ptr,Msg.y);
                Cms.End_Class_Var(Cms_Ptr,"y");
                Cms.Begin_Class_Var(Cms_Ptr,"z");
                Update_Internal_PM_CARTESIAN(Cms_Ptr,Msg.z);
                Cms.End_Class_Var(Cms_Ptr,"z");
                Cms.End_Class(Cms_Ptr,"PM_ROTATION_MATRIX","");
        end Update_Internal_PM_ROTATION_MATRIX;


        procedure Update_PmCircle(Cms_Ptr : in Cms.Cms_Access; Msg : in PmCircle_Access) is
        begin
                Cms.Begin_Class(Cms_Ptr,"PmCircle","");
                Cms.Begin_Class_Var(Cms_Ptr,"center");
                Update_Internal_PmCartesian(Cms_Ptr,Msg.center);
                Cms.End_Class_Var(Cms_Ptr,"center");
                Cms.Begin_Class_Var(Cms_Ptr,"normal");
                Update_Internal_PmCartesian(Cms_Ptr,Msg.normal);
                Cms.End_Class_Var(Cms_Ptr,"normal");
                Cms.Begin_Class_Var(Cms_Ptr,"rTan");
                Update_Internal_PmCartesian(Cms_Ptr,Msg.rTan);
                Cms.End_Class_Var(Cms_Ptr,"rTan");
                Cms.Begin_Class_Var(Cms_Ptr,"rPerp");
                Update_Internal_PmCartesian(Cms_Ptr,Msg.rPerp);
                Cms.End_Class_Var(Cms_Ptr,"rPerp");
                Cms.Begin_Class_Var(Cms_Ptr,"rHelix");
                Update_Internal_PmCartesian(Cms_Ptr,Msg.rHelix);
                Cms.End_Class_Var(Cms_Ptr,"rHelix");
                Cms.Update_Double(Cms_Ptr, "radius", Msg.radius);
                Cms.Update_Double(Cms_Ptr, "angle", Msg.angle);
                Cms.Update_Double(Cms_Ptr, "spiral", Msg.spiral);
                Cms.End_Class(Cms_Ptr,"PmCircle","");
        end Update_PmCircle;

        procedure Update_Internal_PmCircle(Cms_Ptr : in Cms.Cms_Access; Msg : in out PmCircle) is
        begin
                Cms.Begin_Class(Cms_Ptr,"PmCircle","");
                Cms.Begin_Class_Var(Cms_Ptr,"center");
                Update_Internal_PmCartesian(Cms_Ptr,Msg.center);
                Cms.End_Class_Var(Cms_Ptr,"center");
                Cms.Begin_Class_Var(Cms_Ptr,"normal");
                Update_Internal_PmCartesian(Cms_Ptr,Msg.normal);
                Cms.End_Class_Var(Cms_Ptr,"normal");
                Cms.Begin_Class_Var(Cms_Ptr,"rTan");
                Update_Internal_PmCartesian(Cms_Ptr,Msg.rTan);
                Cms.End_Class_Var(Cms_Ptr,"rTan");
                Cms.Begin_Class_Var(Cms_Ptr,"rPerp");
                Update_Internal_PmCartesian(Cms_Ptr,Msg.rPerp);
                Cms.End_Class_Var(Cms_Ptr,"rPerp");
                Cms.Begin_Class_Var(Cms_Ptr,"rHelix");
                Update_Internal_PmCartesian(Cms_Ptr,Msg.rHelix);
                Cms.End_Class_Var(Cms_Ptr,"rHelix");
                Cms.Update_Double(Cms_Ptr, "radius", Msg.radius);
                Cms.Update_Double(Cms_Ptr, "angle", Msg.angle);
                Cms.Update_Double(Cms_Ptr, "spiral", Msg.spiral);
                Cms.End_Class(Cms_Ptr,"PmCircle","");
        end Update_Internal_PmCircle;


        procedure Update_PmPose(Cms_Ptr : in Cms.Cms_Access; Msg : in PmPose_Access) is
        begin
                Cms.Begin_Class(Cms_Ptr,"PmPose","");
                Cms.Begin_Class_Var(Cms_Ptr,"tran");
                Update_Internal_PmCartesian(Cms_Ptr,Msg.tran);
                Cms.End_Class_Var(Cms_Ptr,"tran");
                Cms.Begin_Class_Var(Cms_Ptr,"rot");
                Update_Internal_PmQuaternion(Cms_Ptr,Msg.rot);
                Cms.End_Class_Var(Cms_Ptr,"rot");
                Cms.End_Class(Cms_Ptr,"PmPose","");
        end Update_PmPose;

        procedure Update_Internal_PmPose(Cms_Ptr : in Cms.Cms_Access; Msg : in out PmPose) is
        begin
                Cms.Begin_Class(Cms_Ptr,"PmPose","");
                Cms.Begin_Class_Var(Cms_Ptr,"tran");
                Update_Internal_PmCartesian(Cms_Ptr,Msg.tran);
                Cms.End_Class_Var(Cms_Ptr,"tran");
                Cms.Begin_Class_Var(Cms_Ptr,"rot");
                Update_Internal_PmQuaternion(Cms_Ptr,Msg.rot);
                Cms.End_Class_Var(Cms_Ptr,"rot");
                Cms.End_Class(Cms_Ptr,"PmPose","");
        end Update_Internal_PmPose;


        procedure Update_PmRotationMatrix(Cms_Ptr : in Cms.Cms_Access; Msg : in PmRotationMatrix_Access) is
        begin
                Cms.Begin_Class(Cms_Ptr,"PmRotationMatrix","");
                Cms.Begin_Class_Var(Cms_Ptr,"x");
                Update_Internal_PmCartesian(Cms_Ptr,Msg.x);
                Cms.End_Class_Var(Cms_Ptr,"x");
                Cms.Begin_Class_Var(Cms_Ptr,"y");
                Update_Internal_PmCartesian(Cms_Ptr,Msg.y);
                Cms.End_Class_Var(Cms_Ptr,"y");
                Cms.Begin_Class_Var(Cms_Ptr,"z");
                Update_Internal_PmCartesian(Cms_Ptr,Msg.z);
                Cms.End_Class_Var(Cms_Ptr,"z");
                Cms.End_Class(Cms_Ptr,"PmRotationMatrix","");
        end Update_PmRotationMatrix;

        procedure Update_Internal_PmRotationMatrix(Cms_Ptr : in Cms.Cms_Access; Msg : in out PmRotationMatrix) is
        begin
                Cms.Begin_Class(Cms_Ptr,"PmRotationMatrix","");
                Cms.Begin_Class_Var(Cms_Ptr,"x");
                Update_Internal_PmCartesian(Cms_Ptr,Msg.x);
                Cms.End_Class_Var(Cms_Ptr,"x");
                Cms.Begin_Class_Var(Cms_Ptr,"y");
                Update_Internal_PmCartesian(Cms_Ptr,Msg.y);
                Cms.End_Class_Var(Cms_Ptr,"y");
                Cms.Begin_Class_Var(Cms_Ptr,"z");
                Update_Internal_PmCartesian(Cms_Ptr,Msg.z);
                Cms.End_Class_Var(Cms_Ptr,"z");
                Cms.End_Class(Cms_Ptr,"PmRotationMatrix","");
        end Update_Internal_PmRotationMatrix;


        procedure Update_PM_HOMOGENEOUS(Cms_Ptr : in Cms.Cms_Access; Msg : in PM_HOMOGENEOUS_Access) is
        begin
                Cms.Begin_Class(Cms_Ptr,"PM_HOMOGENEOUS","");
                Cms.Begin_Class_Var(Cms_Ptr,"tran");
                Update_Internal_PM_CARTESIAN(Cms_Ptr,Msg.tran);
                Cms.End_Class_Var(Cms_Ptr,"tran");
                Cms.Begin_Class_Var(Cms_Ptr,"rot");
                Update_Internal_PM_ROTATION_MATRIX(Cms_Ptr,Msg.rot);
                Cms.End_Class_Var(Cms_Ptr,"rot");
                Cms.End_Class(Cms_Ptr,"PM_HOMOGENEOUS","");
        end Update_PM_HOMOGENEOUS;

        procedure Update_Internal_PM_HOMOGENEOUS(Cms_Ptr : in Cms.Cms_Access; Msg : in out PM_HOMOGENEOUS) is
        begin
                Cms.Begin_Class(Cms_Ptr,"PM_HOMOGENEOUS","");
                Cms.Begin_Class_Var(Cms_Ptr,"tran");
                Update_Internal_PM_CARTESIAN(Cms_Ptr,Msg.tran);
                Cms.End_Class_Var(Cms_Ptr,"tran");
                Cms.Begin_Class_Var(Cms_Ptr,"rot");
                Update_Internal_PM_ROTATION_MATRIX(Cms_Ptr,Msg.rot);
                Cms.End_Class_Var(Cms_Ptr,"rot");
                Cms.End_Class(Cms_Ptr,"PM_HOMOGENEOUS","");
        end Update_Internal_PM_HOMOGENEOUS;


        procedure Update_PM_LINE(Cms_Ptr : in Cms.Cms_Access; Msg : in PM_LINE_Access) is
        begin
                Cms.Begin_Class(Cms_Ptr,"PM_LINE","");
                Cms.Begin_Class_Var(Cms_Ptr,"start");
                Update_Internal_PM_POSE(Cms_Ptr,Msg.start);
                Cms.End_Class_Var(Cms_Ptr,"start");
                Cms.Begin_Class_Var(Cms_Ptr,"end");
                Update_Internal_PM_POSE(Cms_Ptr,Msg.End_Var);
                Cms.End_Class_Var(Cms_Ptr,"end");
                Cms.Begin_Class_Var(Cms_Ptr,"uVec");
                Update_Internal_PM_CARTESIAN(Cms_Ptr,Msg.uVec);
                Cms.End_Class_Var(Cms_Ptr,"uVec");
                Cms.End_Class(Cms_Ptr,"PM_LINE","");
        end Update_PM_LINE;

        procedure Update_Internal_PM_LINE(Cms_Ptr : in Cms.Cms_Access; Msg : in out PM_LINE) is
        begin
                Cms.Begin_Class(Cms_Ptr,"PM_LINE","");
                Cms.Begin_Class_Var(Cms_Ptr,"start");
                Update_Internal_PM_POSE(Cms_Ptr,Msg.start);
                Cms.End_Class_Var(Cms_Ptr,"start");
                Cms.Begin_Class_Var(Cms_Ptr,"end");
                Update_Internal_PM_POSE(Cms_Ptr,Msg.End_Var);
                Cms.End_Class_Var(Cms_Ptr,"end");
                Cms.Begin_Class_Var(Cms_Ptr,"uVec");
                Update_Internal_PM_CARTESIAN(Cms_Ptr,Msg.uVec);
                Cms.End_Class_Var(Cms_Ptr,"uVec");
                Cms.End_Class(Cms_Ptr,"PM_LINE","");
        end Update_Internal_PM_LINE;


        procedure Update_PmHomogeneous(Cms_Ptr : in Cms.Cms_Access; Msg : in PmHomogeneous_Access) is
        begin
                Cms.Begin_Class(Cms_Ptr,"PmHomogeneous","");
                Cms.Begin_Class_Var(Cms_Ptr,"tran");
                Update_Internal_PmCartesian(Cms_Ptr,Msg.tran);
                Cms.End_Class_Var(Cms_Ptr,"tran");
                Cms.Begin_Class_Var(Cms_Ptr,"rot");
                Update_Internal_PmRotationMatrix(Cms_Ptr,Msg.rot);
                Cms.End_Class_Var(Cms_Ptr,"rot");
                Cms.End_Class(Cms_Ptr,"PmHomogeneous","");
        end Update_PmHomogeneous;

        procedure Update_Internal_PmHomogeneous(Cms_Ptr : in Cms.Cms_Access; Msg : in out PmHomogeneous) is
        begin
                Cms.Begin_Class(Cms_Ptr,"PmHomogeneous","");
                Cms.Begin_Class_Var(Cms_Ptr,"tran");
                Update_Internal_PmCartesian(Cms_Ptr,Msg.tran);
                Cms.End_Class_Var(Cms_Ptr,"tran");
                Cms.Begin_Class_Var(Cms_Ptr,"rot");
                Update_Internal_PmRotationMatrix(Cms_Ptr,Msg.rot);
                Cms.End_Class_Var(Cms_Ptr,"rot");
                Cms.End_Class(Cms_Ptr,"PmHomogeneous","");
        end Update_Internal_PmHomogeneous;


        procedure Update_PmLine(Cms_Ptr : in Cms.Cms_Access; Msg : in PmLine_Access) is
        begin
                Cms.Begin_Class(Cms_Ptr,"PmLine","");
                Cms.Begin_Class_Var(Cms_Ptr,"start");
                Update_Internal_PmPose(Cms_Ptr,Msg.start);
                Cms.End_Class_Var(Cms_Ptr,"start");
                Cms.Begin_Class_Var(Cms_Ptr,"end");
                Update_Internal_PmPose(Cms_Ptr,Msg.End_Var);
                Cms.End_Class_Var(Cms_Ptr,"end");
                Cms.Begin_Class_Var(Cms_Ptr,"uVec");
                Update_Internal_PmCartesian(Cms_Ptr,Msg.uVec);
                Cms.End_Class_Var(Cms_Ptr,"uVec");
                Cms.Begin_Class_Var(Cms_Ptr,"qVec");
                Update_Internal_PmQuaternion(Cms_Ptr,Msg.qVec);
                Cms.End_Class_Var(Cms_Ptr,"qVec");
                Cms.Update_Double(Cms_Ptr, "tmag", Msg.tmag);
                Cms.Update_Double(Cms_Ptr, "rmag", Msg.rmag);
                Cms.Update_Int(Cms_Ptr, "tmag_is_greater_than_rmag", Msg.tmag_is_greater_than_rmag);
                Cms.Update_Int(Cms_Ptr, "tmag_zero", Msg.tmag_zero);
                Cms.Update_Int(Cms_Ptr, "rmag_zero", Msg.rmag_zero);
                Cms.End_Class(Cms_Ptr,"PmLine","");
        end Update_PmLine;

        procedure Update_Internal_PmLine(Cms_Ptr : in Cms.Cms_Access; Msg : in out PmLine) is
        begin
                Cms.Begin_Class(Cms_Ptr,"PmLine","");
                Cms.Begin_Class_Var(Cms_Ptr,"start");
                Update_Internal_PmPose(Cms_Ptr,Msg.start);
                Cms.End_Class_Var(Cms_Ptr,"start");
                Cms.Begin_Class_Var(Cms_Ptr,"end");
                Update_Internal_PmPose(Cms_Ptr,Msg.End_Var);
                Cms.End_Class_Var(Cms_Ptr,"end");
                Cms.Begin_Class_Var(Cms_Ptr,"uVec");
                Update_Internal_PmCartesian(Cms_Ptr,Msg.uVec);
                Cms.End_Class_Var(Cms_Ptr,"uVec");
                Cms.Begin_Class_Var(Cms_Ptr,"qVec");
                Update_Internal_PmQuaternion(Cms_Ptr,Msg.qVec);
                Cms.End_Class_Var(Cms_Ptr,"qVec");
                Cms.Update_Double(Cms_Ptr, "tmag", Msg.tmag);
                Cms.Update_Double(Cms_Ptr, "rmag", Msg.rmag);
                Cms.Update_Int(Cms_Ptr, "tmag_is_greater_than_rmag", Msg.tmag_is_greater_than_rmag);
                Cms.Update_Int(Cms_Ptr, "tmag_zero", Msg.tmag_zero);
                Cms.Update_Int(Cms_Ptr, "rmag_zero", Msg.rmag_zero);
                Cms.End_Class(Cms_Ptr,"PmLine","");
        end Update_Internal_PmLine;




        function Format(Nml_Type : in long;
                        Msg : in NmlMsg_Access;
                        Cms_Ptr : in Cms.Cms_Access)
                                return int is
        begin
                return 1;
        end Format;

end posemath_n_ada;

-- End of Ada Body file  posemath_n_ada.adb

