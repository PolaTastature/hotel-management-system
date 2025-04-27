package test1;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.54.0)",
    comments = "Source: hotelMotel.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class HotelServiceGrpc {

  private HotelServiceGrpc() {}

  public static final String SERVICE_NAME = "HotelService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<test1.UpitZaHotele,
      test1.Hotel> getUpitMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Upit",
      requestType = test1.UpitZaHotele.class,
      responseType = test1.Hotel.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<test1.UpitZaHotele,
      test1.Hotel> getUpitMethod() {
    io.grpc.MethodDescriptor<test1.UpitZaHotele, test1.Hotel> getUpitMethod;
    if ((getUpitMethod = HotelServiceGrpc.getUpitMethod) == null) {
      synchronized (HotelServiceGrpc.class) {
        if ((getUpitMethod = HotelServiceGrpc.getUpitMethod) == null) {
          HotelServiceGrpc.getUpitMethod = getUpitMethod =
              io.grpc.MethodDescriptor.<test1.UpitZaHotele, test1.Hotel>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Upit"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  test1.UpitZaHotele.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  test1.Hotel.getDefaultInstance()))
              .setSchemaDescriptor(new HotelServiceMethodDescriptorSupplier("Upit"))
              .build();
        }
      }
    }
    return getUpitMethod;
  }

  private static volatile io.grpc.MethodDescriptor<test1.RezervacijaInfo,
      test1.Rezervacija> getRezervisiMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Rezervisi",
      requestType = test1.RezervacijaInfo.class,
      responseType = test1.Rezervacija.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<test1.RezervacijaInfo,
      test1.Rezervacija> getRezervisiMethod() {
    io.grpc.MethodDescriptor<test1.RezervacijaInfo, test1.Rezervacija> getRezervisiMethod;
    if ((getRezervisiMethod = HotelServiceGrpc.getRezervisiMethod) == null) {
      synchronized (HotelServiceGrpc.class) {
        if ((getRezervisiMethod = HotelServiceGrpc.getRezervisiMethod) == null) {
          HotelServiceGrpc.getRezervisiMethod = getRezervisiMethod =
              io.grpc.MethodDescriptor.<test1.RezervacijaInfo, test1.Rezervacija>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Rezervisi"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  test1.RezervacijaInfo.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  test1.Rezervacija.getDefaultInstance()))
              .setSchemaDescriptor(new HotelServiceMethodDescriptorSupplier("Rezervisi"))
              .build();
        }
      }
    }
    return getRezervisiMethod;
  }

  private static volatile io.grpc.MethodDescriptor<test1.RezervacijaInfo,
      test1.Rezervacija> getOtkaziRezervacijuMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "otkaziRezervaciju",
      requestType = test1.RezervacijaInfo.class,
      responseType = test1.Rezervacija.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<test1.RezervacijaInfo,
      test1.Rezervacija> getOtkaziRezervacijuMethod() {
    io.grpc.MethodDescriptor<test1.RezervacijaInfo, test1.Rezervacija> getOtkaziRezervacijuMethod;
    if ((getOtkaziRezervacijuMethod = HotelServiceGrpc.getOtkaziRezervacijuMethod) == null) {
      synchronized (HotelServiceGrpc.class) {
        if ((getOtkaziRezervacijuMethod = HotelServiceGrpc.getOtkaziRezervacijuMethod) == null) {
          HotelServiceGrpc.getOtkaziRezervacijuMethod = getOtkaziRezervacijuMethod =
              io.grpc.MethodDescriptor.<test1.RezervacijaInfo, test1.Rezervacija>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "otkaziRezervaciju"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  test1.RezervacijaInfo.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  test1.Rezervacija.getDefaultInstance()))
              .setSchemaDescriptor(new HotelServiceMethodDescriptorSupplier("otkaziRezervaciju"))
              .build();
        }
      }
    }
    return getOtkaziRezervacijuMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static HotelServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<HotelServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<HotelServiceStub>() {
        @java.lang.Override
        public HotelServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new HotelServiceStub(channel, callOptions);
        }
      };
    return HotelServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static HotelServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<HotelServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<HotelServiceBlockingStub>() {
        @java.lang.Override
        public HotelServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new HotelServiceBlockingStub(channel, callOptions);
        }
      };
    return HotelServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static HotelServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<HotelServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<HotelServiceFutureStub>() {
        @java.lang.Override
        public HotelServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new HotelServiceFutureStub(channel, callOptions);
        }
      };
    return HotelServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void upit(test1.UpitZaHotele request,
        io.grpc.stub.StreamObserver<test1.Hotel> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUpitMethod(), responseObserver);
    }

    /**
     */
    default void rezervisi(test1.RezervacijaInfo request,
        io.grpc.stub.StreamObserver<test1.Rezervacija> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRezervisiMethod(), responseObserver);
    }

    /**
     */
    default void otkaziRezervaciju(test1.RezervacijaInfo request,
        io.grpc.stub.StreamObserver<test1.Rezervacija> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getOtkaziRezervacijuMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service HotelService.
   */
  public static abstract class HotelServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return HotelServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service HotelService.
   */
  public static final class HotelServiceStub
      extends io.grpc.stub.AbstractAsyncStub<HotelServiceStub> {
    private HotelServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HotelServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new HotelServiceStub(channel, callOptions);
    }

    /**
     */
    public void upit(test1.UpitZaHotele request,
        io.grpc.stub.StreamObserver<test1.Hotel> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getUpitMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void rezervisi(test1.RezervacijaInfo request,
        io.grpc.stub.StreamObserver<test1.Rezervacija> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRezervisiMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void otkaziRezervaciju(test1.RezervacijaInfo request,
        io.grpc.stub.StreamObserver<test1.Rezervacija> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getOtkaziRezervacijuMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service HotelService.
   */
  public static final class HotelServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<HotelServiceBlockingStub> {
    private HotelServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HotelServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new HotelServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public java.util.Iterator<test1.Hotel> upit(
        test1.UpitZaHotele request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getUpitMethod(), getCallOptions(), request);
    }

    /**
     */
    public test1.Rezervacija rezervisi(test1.RezervacijaInfo request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRezervisiMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<test1.Rezervacija> otkaziRezervaciju(
        test1.RezervacijaInfo request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getOtkaziRezervacijuMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service HotelService.
   */
  public static final class HotelServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<HotelServiceFutureStub> {
    private HotelServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HotelServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new HotelServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<test1.Rezervacija> rezervisi(
        test1.RezervacijaInfo request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRezervisiMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_UPIT = 0;
  private static final int METHODID_REZERVISI = 1;
  private static final int METHODID_OTKAZI_REZERVACIJU = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_UPIT:
          serviceImpl.upit((test1.UpitZaHotele) request,
              (io.grpc.stub.StreamObserver<test1.Hotel>) responseObserver);
          break;
        case METHODID_REZERVISI:
          serviceImpl.rezervisi((test1.RezervacijaInfo) request,
              (io.grpc.stub.StreamObserver<test1.Rezervacija>) responseObserver);
          break;
        case METHODID_OTKAZI_REZERVACIJU:
          serviceImpl.otkaziRezervaciju((test1.RezervacijaInfo) request,
              (io.grpc.stub.StreamObserver<test1.Rezervacija>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getUpitMethod(),
          io.grpc.stub.ServerCalls.asyncServerStreamingCall(
            new MethodHandlers<
              test1.UpitZaHotele,
              test1.Hotel>(
                service, METHODID_UPIT)))
        .addMethod(
          getRezervisiMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              test1.RezervacijaInfo,
              test1.Rezervacija>(
                service, METHODID_REZERVISI)))
        .addMethod(
          getOtkaziRezervacijuMethod(),
          io.grpc.stub.ServerCalls.asyncServerStreamingCall(
            new MethodHandlers<
              test1.RezervacijaInfo,
              test1.Rezervacija>(
                service, METHODID_OTKAZI_REZERVACIJU)))
        .build();
  }

  private static abstract class HotelServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    HotelServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return test1.HotelMotel.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("HotelService");
    }
  }

  private static final class HotelServiceFileDescriptorSupplier
      extends HotelServiceBaseDescriptorSupplier {
    HotelServiceFileDescriptorSupplier() {}
  }

  private static final class HotelServiceMethodDescriptorSupplier
      extends HotelServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    HotelServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (HotelServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new HotelServiceFileDescriptorSupplier())
              .addMethod(getUpitMethod())
              .addMethod(getRezervisiMethod())
              .addMethod(getOtkaziRezervacijuMethod())
              .build();
        }
      }
    }
    return result;
  }
}
