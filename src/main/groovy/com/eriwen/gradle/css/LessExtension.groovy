package com.eriwen.gradle.css

import org.gradle.api.tasks.Input

/**
 * Created with IntelliJ IDEA.
 * User: joe
 * Date: 2/12/13
 * Time: 12:58 PM
 * To change this template use File | Settings | File Templates.
 */
class LessExtension {
    public static final String NAME = 'less'

    @Input LinkedHashMap<String, Object> options = []
}
