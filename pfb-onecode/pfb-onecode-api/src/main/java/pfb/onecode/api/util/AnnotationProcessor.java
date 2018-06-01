package pfb.onecode.api.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * 注解处理工具类
 * @author libingyang
 * @date 2018年5月17日下午2:35:26
 */
public class AnnotationProcessor {
	/**
	 * 序列
	 * @return
	 * @return:int
	 * @author:libingyang
	 * @date:2018年5月17日 下午2:37:10
	 */
	@SuppressWarnings({"unchecked"})
	public static<T> int getSequence(Field field,T t){
		if(field.isAnnotationPresent((Class<? extends Annotation>) t.getClass())){   //是否使用***注解
			// 获取所有注解
			Annotation[] declaredAnnotations = field.getDeclaredAnnotations();
			for (Annotation ann : declaredAnnotations) {
				if (ann.annotationType().equals(t.getClass())) {
					return Integer.parseInt(ann.toString());
				}
			}
		}
		return 0;
	}
}
