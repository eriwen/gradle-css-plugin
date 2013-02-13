/**
 * Copyright 2012 Eric Wendelin
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.eriwen.gradle.css

import org.gradle.api.tasks.Input

class CssLintExtension {
    public static final NAME = "csslint"
    @Input String format = 'compact'
    @Input Iterable<String> errors = []
    @Input Iterable<String> warnings = ['important','adjoining-classes','known-properties','box-sizing','box-model','outline-none','duplicate-background-images','compatible-vendor-prefixes','display-property-grouping','qualified-headings','fallback-colors','duplicate-properties','empty-rules','errors','shorthand','ids','gradients','font-sizes','font-faces','floats','underscore-property-hack','overqualified-elements','import','regex-selectors','rules-count','star-property-hack','text-indent','unique-headings','universal-selector','unqualified-attributes','vendor-prefix','zero-units']
}
