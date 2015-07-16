

package com.hep.terminalhep.inject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
* @ClassName: PreferenceInject
* @Description: TODO
* @author zhangj
* @date 2015年4月28日
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PreferenceInject {
    String value();
}
