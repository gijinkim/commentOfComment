package com.example.commentofcomment.advanced


class AdvancedTest {


}


val a = "a"
val b = "b"
val c = "c"
val d = "d"
val e = "e"
val f = "f"
val g = "g"
val h = "h"
val i = "i"
val j = "j"

val user1 = "1"
val user2 = "2"
val user3 = "3"

data class Comment(
    //댓글 ID
    val id: String,
    //댓글의 부모 댓글
    val parentId: String? = null,
    //대댓글의 최초 댓글
    val originId: String? = null,
    val from: String
)

val commentList = listOf(
    Comment(id = a, from = user1),
    Comment(id = b, from = user2),
    Comment(id = c, originId = a, parentId = a, from = user3),
    Comment(id = d, from = user3),
    Comment(id = e, originId = b, parentId = b, from = user1),
    Comment(id = f, originId = a, parentId = c, from = user2),
    Comment(id = g, from = user1),
    Comment(id = h, originId = d, parentId = d, from = user2),
    Comment(id = i, originId = a, parentId = f, from = user1),
    Comment(id = j, from = user3),
)

data class CommentResponse(
    val id: String,
    val from: String,
    val to: String? = null,
    val commentList: MutableList<CommentResponse> = mutableListOf()
)

val expectedAnswer = listOf(
    CommentResponse(a, user1).also {
        it.commentList.addAll(
            listOf(
                CommentResponse(c, user3, user1),
                CommentResponse(f, user2, user3),
                CommentResponse(i, user1, user2)
            )
        )
    },
    CommentResponse(b, user2).also { it.commentList.addAll(listOf(CommentResponse(e, user1, user2))) },
    CommentResponse(d, user3).also { it.commentList.addAll(listOf(CommentResponse(h, user2, user3))) },
    CommentResponse(g, user1),
    CommentResponse(j, user3),
)