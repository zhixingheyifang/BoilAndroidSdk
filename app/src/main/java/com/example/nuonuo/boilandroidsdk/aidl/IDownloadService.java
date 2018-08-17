package com.example.nuonuo.boilandroidsdk.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/**
 * IDownloadService 这个类在两个进程中都能访问到（另个进程是在一个apk包中，不是在两个apk）
 */
public interface IDownloadService extends IInterface {

    public void download(java.lang.String url) throws RemoteException;//下载

    public void delete(java.lang.String url) throws RemoteException; //删除下载任务

    public void stop(java.lang.String url) throws RemoteException;//停止下载任务

    public int getQueueSize() throws RemoteException; //获取下载队列大小

    /**
     * 帮server端写onTransact
     */
    public static abstract class Stub extends Binder implements IDownloadService {

        // 注意，通过以下代码我们发现，我们在.aidl中定义的方法名字其实没什么意义，服务器端根本没有使         // 用这些名字，而是自动为这些方法生成了递增的ID，根据方法的顺序而不是名字来一个一个处理
        static final int TRANSACTION_download = (IBinder.FIRST_CALL_TRANSACTION + 0);
        static final int TRANSACTION_delete = (IBinder.FIRST_CALL_TRANSACTION + 1);
        static final int TRANSACTION_stop = (IBinder.FIRST_CALL_TRANSACTION + 2);
        static final int TRANSACTION_getQueueSize = (IBinder.FIRST_CALL_TRANSACTION + 3);
        private static final java.lang.String DESCRIPTOR = "IDownloadService";

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        /**
         *
         * 注：
         * 这个方法里面有个处理，通过queryLocalInterface查询，如果服务端和客户端都是在同一个进程，那* 么就不需要跨进程了，直接将IDownloadService当做普通的对象来使用即可，否则返回远程对象
         * 的代理
         */
        public static IDownloadService asInterface(IBinder obj) {
            if ((obj == null)) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (((iin != null) && (iin instanceof IDownloadService))) {
                return ((IDownloadService) iin);
            }
            return new IDownloadService.Stub.Proxy(obj);
        }

        /**
         * 返回Binder实例
         */
        @Override
        public IBinder asBinder() {
            return this;
        }

        /**
         * 根据code参数来处理，这里面会调用真正的业务实现类
         */
        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case INTERFACE_TRANSACTION: {
                    reply.writeString(DESCRIPTOR);
                    return true;
                }
                case TRANSACTION_download: {
                    data.enforceInterface(DESCRIPTOR);
                    java.lang.String _arg0;
                    _arg0 = data.readString();
                    this.download(_arg0);
                    reply.writeNoException();
                    return true;
                }
                case TRANSACTION_delete: {
                    data.enforceInterface(DESCRIPTOR);
                    java.lang.String _arg0;
                    _arg0 = data.readString();
                    this.delete(_arg0);
                    reply.writeNoException();
                    return true;
                }
                case TRANSACTION_stop: {
                    data.enforceInterface(DESCRIPTOR);
                    java.lang.String _arg0;
                    _arg0 = data.readString();
                    this.stop(_arg0);
                    reply.writeNoException();
                    return true;
                }
                case TRANSACTION_getQueueSize: {
                    data.enforceInterface(DESCRIPTOR);
                    int _result = this.getQueueSize();
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                }
            }
            return super.onTransact(code, data, reply, flags);
        }

        //帮client端写调用代码
        private static class Proxy implements IDownloadService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                mRemote = remote;
            }

            //Proxy的asBinder()返回位于本地接口的远程代理
            @Override
            public IBinder asBinder() {
                return mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            /**
             * 下载
             *
             * @param url
             */
            @Override
            public void download(java.lang.String url) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeString(url);

                    //Proxy中， 业务接口中其实没有做任何业务逻辑处理，仅仅是收集本地参数，序列化
                    //后通过IBinder的transact方法，发给服务器端，并且通过_reply来接收服务器端
                    //的处理结果
                    mRemote.transact(Stub.TRANSACTION_download, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            /**
             * 删除下载任务
             *
             * @param url
             */
            @Override
            public void delete(java.lang.String url) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeString(url);
                    mRemote.transact(Stub.TRANSACTION_delete, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            /**
             * 停止下载任务
             *
             * @param url
             */
            @Override
            public void stop(java.lang.String url) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeString(url);
                    mRemote.transact(Stub.TRANSACTION_stop, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            /**
             * 获取下载队列大小
             *
             * @return
             */
            @Override
            public int getQueueSize() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                int _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    mRemote.transact(Stub.TRANSACTION_getQueueSize, _data, _reply, 0);
                    _reply.readException();
                    _result = _reply.readInt();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }
        }
    }
}