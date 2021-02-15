/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.ide.schema

import com.intellij.ide.annotator.AnnotatorBase
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.psi.PsiElement
import org.toml.lang.psi.TomlElement
import org.toml.lang.psi.TomlFile
import org.toml.lang.psi.TomlKeyValue
import org.toml.lang.psi.TomlTable

class TomlNormalizedAnnotator : AnnotatorBase() {
    override fun annotateInternal(element: PsiElement, holder: AnnotationHolder) {
//        if (element is TomlElement) {
//            val path = element.path
//            println("$element(${element.text}) \n ${path.joinToString(".")}")
//            println()
//        }
    }
}

val TomlElement.path: List<String>
    get() {
        var current = when (this) {
            is TomlKeyValue -> this.key.segments.map { it.text }
            is TomlTable -> this.header.key?.segments?.map { it.text }
            else -> null
        } ?: emptyList()

        if (this.parent !is TomlFile) {
            val parentPath = (this.parent as TomlElement).path
            current = parentPath + current
        }

        return current
    }
