package BankApp;

/**
* BankApp/BankHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from bank.idl
* Wednesday, September 28, 2022 12:35:00 AM CAT
*/

public final class BankHolder implements org.omg.CORBA.portable.Streamable
{
  public BankApp.Bank value = null;

  public BankHolder ()
  {
  }

  public BankHolder (BankApp.Bank initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = BankApp.BankHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    BankApp.BankHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return BankApp.BankHelper.type ();
  }

}
