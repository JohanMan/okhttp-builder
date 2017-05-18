# okhttp-builder
OkHttp加强，类似于Retrofit

## 使用方法：

```
TestHttpApi api = HttpBuilder.create(TestHttpApi.class);
HttpCall<String> httpCall = api.testBaidu();
httpCall.call(new HttpCallback<String>() {
    @Override
    public void onSuccess(String data) {
        Log.e(getClass().getName(), "data : " + data);
    }
    @Override
    public void onError(Exception e) {
        e.printStackTrace();
    }
});
```
