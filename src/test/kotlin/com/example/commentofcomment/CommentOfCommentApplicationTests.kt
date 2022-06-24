package com.example.commentofcomment

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CommentOfCommentApplicationTests {

//    @Test
//    fun contextLoads() {
//        println("comments : \n" + commentList.joinToString(separator = "\n"))
//
//        val answer = solve(commentList)
//
//        println(
//            "answer : \n" + answer.joinToString(separator = "\n")
//        )
//
//        Assertions.assertEquals(answer, expectedAnswer)
//    }

    fun solve(list: List<Comment>) : List<Pair<String?, CommentResponse?>>? {

        val commentIterator = list.listIterator()
        var comment: Comment?
        var responseMap : MutableMap<String?, CommentResponse?> = mutableMapOf()

        while (commentIterator.hasNext())   {
            comment = commentIterator.next()
            //댓글을 CommentResponse로 만들어서 map에 저장한다.


            //부모 댓글 아이디가 있으면 map에서 찾는다.
            val isNestedComment = comment.parentId != null
            if (isNestedComment) {
                //부모 댓글의 CommentResponse를 찾는다
                //부모 CommentResponse에 대댓글을 추가하고 다시 맵에 저장한다
                responseMap[comment.parentId]?.commentList?.add(CommentResponse(comment.id))

                // call by value && call by reference 공부하세요.
                // 밑에 코드는 필요없습니다
//                responseMap[comment.parentId] = commentResponse
            } else {
                responseMap[comment.id] = CommentResponse(comment.id)
            }
        }

        return responseMap.toList()
    }

    @Test
    fun ex(){

        println(solve(commentList)?.joinToString(separator = "\n"))

    }

}

data class Comment(
    //댓글 ID
    val id: String,
    //댓글의 부모 댓글
    val parentId: String? = null,
    //대댓글의 최초 댓글
    val originId: String? = null,
)

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


val commentList = listOf(
    Comment(id = a),
    Comment(id = b),
    Comment(id = c, parentId = a),
    Comment(id = d),
    Comment(id = e, parentId = b),
    Comment(id = f, parentId = a),
    Comment(id = g),
    Comment(id = h, parentId = d),
    Comment(id = i),
    Comment(id = j),
)

data class CommentResponse(
    val id: String,
    val commentList: MutableList<CommentResponse> = mutableListOf()
) {
}

val expectedAnswer = listOf(
    CommentResponse(a).also { it.commentList.addAll(listOf(CommentResponse(c), CommentResponse(f))) },
    CommentResponse(b).also { it.commentList.addAll(listOf(CommentResponse(e))) },
    CommentResponse(d).also { it.commentList.addAll(listOf(CommentResponse(h))) },
    CommentResponse(g),
    CommentResponse(i),
    CommentResponse(j),
)