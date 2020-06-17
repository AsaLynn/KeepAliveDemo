/*
 * This file is auto-generated.  DO NOT MODIFY.
 */
package com.zxn.process.service;
public interface GuardAidl extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.zxn.process.service.GuardAidl
{
private static final java.lang.String DESCRIPTOR = "com.zxn.process.service.GuardAidl";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.zxn.process.service.GuardAidl interface,
 * generating a proxy if needed.
 */
public static com.zxn.process.service.GuardAidl asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.zxn.process.service.GuardAidl))) {
return ((com.zxn.process.service.GuardAidl)iin);
}
return new com.zxn.process.service.GuardAidl.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
java.lang.String descriptor = DESCRIPTOR;
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(descriptor);
return true;
}
case TRANSACTION_wakeUp:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
int _arg2;
_arg2 = data.readInt();
this.wakeUp(_arg0, _arg1, _arg2);
reply.writeNoException();
return true;
}
default:
{
return super.onTransact(code, data, reply, flags);
}
}
}
private static class Proxy implements com.zxn.process.service.GuardAidl
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
//鐩镐簰鍞ら啋鏈嶅姟

@Override public void wakeUp(java.lang.String title, java.lang.String discription, int iconRes) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(title);
_data.writeString(discription);
_data.writeInt(iconRes);
mRemote.transact(Stub.TRANSACTION_wakeUp, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_wakeUp = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
//鐩镐簰鍞ら啋鏈嶅姟

public void wakeUp(java.lang.String title, java.lang.String discription, int iconRes) throws android.os.RemoteException;
}
