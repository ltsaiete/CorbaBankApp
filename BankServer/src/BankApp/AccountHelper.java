package BankApp;


/**
* BankApp/AccountHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from bank.idl
* Wednesday, September 28, 2022 12:35:00 AM CAT
*/

abstract public class AccountHelper
{
  private static String  _id = "IDL:BankApp/Account:1.0";

  public static void insert (org.omg.CORBA.Any a, BankApp.Account that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static BankApp.Account extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (BankApp.AccountHelper.id (), "Account");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static BankApp.Account read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_AccountStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, BankApp.Account value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static BankApp.Account narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof BankApp.Account)
      return (BankApp.Account)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      BankApp._AccountStub stub = new BankApp._AccountStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static BankApp.Account unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof BankApp.Account)
      return (BankApp.Account)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      BankApp._AccountStub stub = new BankApp._AccountStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
