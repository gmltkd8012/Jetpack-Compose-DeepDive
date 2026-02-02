package com.korino.study.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key

/**
 * 컴포저블의 그룹
 */


/**
 * 이동 가능한 그룹
 */
data class Talk(val id: String)

@Composable
fun TalksScreen(talks: List<Talk>) {
    Column {
        for (talk in talks) {
            key(talk.id) {  // 고유 키로 감싸기
                Talk(talk)   // 이동 가능한 그룹 생성
            }
        }
    }
}

@Composable
fun Talk(talk: Talk) {}