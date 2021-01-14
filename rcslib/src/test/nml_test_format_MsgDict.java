/*
*	New Java File starts here.
*	This file should be named nml_test_format_MsgDict.java
*	Automatically generated by NML CodeGen Java Applet.
*	with command line arguments :  generate_for_all_langs=true HHFile=nml_test_format.hh
*
*	.gen script :
*		0:load nml_test_format.hh
*		1:clear
*		2:select_from_file nml_test_format.hh
*		3:generate C protos>nml_test_format_c_n.h
*		4:generate C format>nml_test_format_c_n.c
*		5:generate C update>nml_test_format_c_n.c
*		6:generate C++ protos>nml_test_format_n_codegen_protos.hh
*		7:generate C++ format>nml_test_format_n.cc
*		8:generate C++ update>nml_test_format_n.cc
*		9:generate C++ constructor>nml_test_format_n.cc
*		10:generate Java dict>nml_test_format_MsgDict.java
*		11:generate Java classes >*
*		12:generate Ada spec>nml_test_format_n_ada.ads
*		13:generate Ada body>nml_test_format_n_ada.adb
*		14:exit
*
*/

// Import NML classes and interfaces
import rcs.nml.*;

import java.util.Hashtable;
/*
*	Class definition for nml_test_format_MsgDict
*	Automatically generated by NML CodeGen Java Applet.
*/
public class nml_test_format_MsgDict implements NMLMessageDictionary
{

	// Define an object of every message class.
	private BOP_MSG BOP_MSG_object = null;
	private MyStat MyStat_object = null;
	private MyStat2 MyStat2_object = null;
	private QTEST_MSG QTEST_MSG_object = null;
	private SIMPLER_MSG SIMPLER_MSG_object = null;
	private TEST_MESSAGE TEST_MESSAGE_object = null;
	private TEST_MESSAGE_BASE TEST_MESSAGE_BASE_object = null;

	// ID Type Constants
	public static final int BOP_MSG_TYPE  = 104;
	public static final int MyStat_TYPE  = 1001;
	public static final int MyStat2_TYPE  = 2002;
	public static final int QTEST_MSG_TYPE  = 103;
	public static final int SIMPLER_MSG_TYPE  = 102;
	public static final int TEST_MESSAGE_TYPE  = 101;
	public static final int TEST_MESSAGE_BASE_TYPE  = 100;

	// Sizes useful for C++ compat and preallocating byte storage. 
	//(not used for normal NML reads/writes).
	public long getEstimatedSize(final int _type)
	{
		switch(_type)
		{
			case BOP_MSG_TYPE: /*-2*/
				return 32;
			case MyStat_TYPE: /*-2*/
				return 128;
			case MyStat2_TYPE: /*-2*/
				return 296;
			case QTEST_MSG_TYPE: /*-2*/
				return 168;
			case SIMPLER_MSG_TYPE: /*-2*/
				return 112;
			case TEST_MESSAGE_TYPE: /*-2*/
				return 6168;
			case TEST_MESSAGE_BASE_TYPE: /*-2*/
				return 304;
		default:
			break;
		}
		return 6168; /* maximum */
	}

	public long getMaxEstimatedSize() {
		return 6168;
	}



	//Define an NML_ENUM_INFO object for the type ID's
	NML_ENUM_INFO nml_enum_info_for_type_names =null;


	//Create a constructor to setup the NML_ENUM_INFO object.
	public nml_test_format_MsgDict()
	{
		nml_enum_info_for_type_names= new NML_ENUM_INFO();
		nml_enum_info_for_type_names.name="nml_test_format_MsgDict";
		Hashtable h1 = new Hashtable();
		Hashtable h2 = new Hashtable();
		Integer I = null;
		String  S = null;
		I=new Integer(BOP_MSG_TYPE);
		S="BOP_MSG";
		h1.put(I,S); h2.put(S,I);
		I=new Integer(MyStat_TYPE);
		S="MyStat";
		h1.put(I,S); h2.put(S,I);
		I=new Integer(MyStat2_TYPE);
		S="MyStat2";
		h1.put(I,S); h2.put(S,I);
		I=new Integer(QTEST_MSG_TYPE);
		S="QTEST_MSG";
		h1.put(I,S); h2.put(S,I);
		I=new Integer(SIMPLER_MSG_TYPE);
		S="SIMPLER_MSG";
		h1.put(I,S); h2.put(S,I);
		I=new Integer(TEST_MESSAGE_TYPE);
		S="TEST_MESSAGE";
		h1.put(I,S); h2.put(S,I);
		I=new Integer(TEST_MESSAGE_BASE_TYPE);
		S="TEST_MESSAGE_BASE";
		h1.put(I,S); h2.put(S,I);
		nml_enum_info_for_type_names.int_to_string_hash=h1;
		nml_enum_info_for_type_names.string_to_int_hash=h2;
	}



	// Miscellaneous Pre-Defined Values
	public static final int NTFHH_BASE  = 100;
	public static final int ONE_HUNDRED  = 100;
	public static final int TWO  = 2;
	public static final String fwPixel  = "unsigned short";
	public static final int num_object_classes  = 28;
	public static final int two  = 2;

	// Enumerated Type Constants

	// RCS_ADMIN_STATE
	public static final int ADMIN_INITIALIZED=2;
	public static final int ADMIN_SHUT_DOWN=3;
	public static final int ADMIN_UNINITIALIZED=1;
	public static final int RCS_ADMIN_ZERO=0;

	// enumtest_typedef
	public static final int xxx=2;
	public static final int yyy=1;
	public static final int zzz=0;

	// RCS_STATUS
	public static final int RCS_DONE=1;
	public static final int RCS_ERROR=3;
	public static final int RCS_EXEC=2;
	public static final int UNINITIALIZED_STATUS=-1;

	// enumtest3ftoh
	public static final int fff=0;
	public static final int ggg=1;
	public static final int hhh=2;

	// enumtest_typedef2
	public static final int uuu=2;
	public static final int vvv=1;
	public static final int www=0;

	// enumtest
	public static final int a=0;
	public static final int aa=2;
	public static final int b=1;
	public static final int bb=3;
	public static final int ccc=99;
	public static final int dd=77;
	public static final int e=88;

	// anonymous_enum_nml_test_format_hh_82
	public static final int BUILDING=4;
	public static final int BUILDINGCONNECTOR=7;
	public static final int CONCRETE=8;
	public static final int DRIVEPAVED=6;
	public static final int INIT=0;
	public static final int LAKE=11;
	public static final int LAMP=18;
	public static final int LIGHTPOLE=17;
	public static final int PARKINGPAVED=3;
	public static final int SHRUB=14;
	public static final int SIDEWALK=1;
	public static final int SIGN=16;
	public static final int STEPS=9;
	public static final int SUBSTATION=10;
	public static final int TREE=15;
	public static final int TREES=5;
	public static final int UNKNOWN=2;
	public static final int UTILITYBOX=12;
	public static final int UTILITYPOLE=13;


	// NML Format Function
	public int formatMsg(NMLFormatConverter nml_fc)
	{
		int return_val=0;
		nml_fc.check_type_info(nml_enum_info_for_type_names);

		switch(nml_fc.msg_type)
		{
		case BOP_MSG_TYPE: /*104*/
			if(null == BOP_MSG_object) { 
				BOP_MSG_object = new BOP_MSG();
			}
			nml_fc.msg_to_update  = BOP_MSG_object;
			BOP_MSG_object.update(nml_fc);
			break;
		case MyStat_TYPE: /*1001*/
			if(null == MyStat_object) { 
				MyStat_object = new MyStat();
			}
			nml_fc.msg_to_update  = MyStat_object;
			MyStat_object.update(nml_fc);
			break;
		case MyStat2_TYPE: /*2002*/
			if(null == MyStat2_object) { 
				MyStat2_object = new MyStat2();
			}
			nml_fc.msg_to_update  = MyStat2_object;
			MyStat2_object.update(nml_fc);
			break;
		case QTEST_MSG_TYPE: /*103*/
			if(null == QTEST_MSG_object) { 
				QTEST_MSG_object = new QTEST_MSG();
			}
			nml_fc.msg_to_update  = QTEST_MSG_object;
			QTEST_MSG_object.update(nml_fc);
			break;
		case SIMPLER_MSG_TYPE: /*102*/
			if(null == SIMPLER_MSG_object) { 
				SIMPLER_MSG_object = new SIMPLER_MSG();
			}
			nml_fc.msg_to_update  = SIMPLER_MSG_object;
			SIMPLER_MSG_object.update(nml_fc);
			break;
		case TEST_MESSAGE_TYPE: /*101*/
			if(null == TEST_MESSAGE_object) { 
				TEST_MESSAGE_object = new TEST_MESSAGE();
			}
			nml_fc.msg_to_update  = TEST_MESSAGE_object;
			TEST_MESSAGE_object.update(nml_fc);
			break;
		case TEST_MESSAGE_BASE_TYPE: /*100*/
			if(null == TEST_MESSAGE_BASE_object) { 
				TEST_MESSAGE_BASE_object = new TEST_MESSAGE_BASE();
			}
			nml_fc.msg_to_update  = TEST_MESSAGE_BASE_object;
			TEST_MESSAGE_BASE_object.update(nml_fc);
			break;

		// Type c_struct has Id less than 1 :  -2

		// Type c_struct2 has Id less than 1 :  -2

		// Type fwLaserStruct has Id less than 1 :  -2

		// Type teststruct has Id less than 1 :  -2

		// Type teststruct_td2 has Id less than 1 :  -2

		default:
			return_val=-1;
			break;
		}
		return(return_val);
	}


	// NML Symbol Lookup Function
	public static String SymbolLookup(int type)
	{
		switch(type)
		{
		case BOP_MSG_TYPE:
			return "BOP_MSG";
		case MyStat_TYPE:
			return "MyStat";
		case MyStat2_TYPE:
			return "MyStat2";
		case QTEST_MSG_TYPE:
			return "QTEST_MSG";
		case SIMPLER_MSG_TYPE:
			return "SIMPLER_MSG";
		case TEST_MESSAGE_TYPE:
			return "TEST_MESSAGE";
		case TEST_MESSAGE_BASE_TYPE:
			return "TEST_MESSAGE_BASE";

		// Type c_struct has Id less than 1 :  -2

		// Type c_struct2 has Id less than 1 :  -2

		// Type fwLaserStruct has Id less than 1 :  -2

		// Type teststruct has Id less than 1 :  -2

		// Type teststruct_td2 has Id less than 1 :  -2

		default:
			break;
		}
		return("!!UNDEFINED_SYMBOL!!");
	}

}
