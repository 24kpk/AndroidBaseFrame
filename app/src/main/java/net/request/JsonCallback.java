package net.request;

import com.lzy.okgo.callback.AbsCallback;

import net.entity.BaseBean;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;

/**
 * author: C_CHEUNG
 * created on: 2016/11/18
 * description: 数据JSON回调
 */
public abstract class JsonCallback<T> extends AbsCallback<T> {
    Type objectType;
    Class baseBeanClass;
    Class childBeanClass;

    public JsonCallback() {

    }
    public JsonCallback(Class childBeanClass) {
        this.childBeanClass = childBeanClass;
    }
//
//    public JsonCallback(Class baseBeanClass, Class childBeanClass) {
//        this.baseBeanClass = baseBeanClass;
//        this.childBeanClass = childBeanClass;
//
//    }

    @Override
    public T convertSuccess(Response response) throws Exception {
        //以下代码是通过泛型解析实际参数,泛型必须传
        //解析泛型嵌套参数写法 new JsonCallback<BaseBean<UserBean>>
        if (childBeanClass == null) throw new IllegalStateException("没有填写泛型参数");


        //添加RESTFULL API 解析过程
        Type genType = childBeanClass.getGenericSuperclass();
//        Type genType = getClass().getGenericSuperclass();
//        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        JsonConvert<T> convert = new JsonConvert<>();
        convert.setType(type(BaseBean.class,childBeanClass));
//        convert.setType(params[0]);
        T t = convert.convertSuccess(response);
        response.close();

        return t;
    }

    private static ParameterizedType type(final Class<?> raw, final Type... args) {
        return new ParameterizedType() {
            public Type getRawType() {
                return raw;
            }

            public Type[] getActualTypeArguments() {
                return args;
            }

            public Type getOwnerType() {
                return null;
            }
        };
    }
}
