package i_introduction._7_Nullable_Types

import util.TODO
import util.doc7

fun test() {
    val s: String = "this variable cannot store null references"
    val q: String? = null

    if (q != null) q.length      // you have to check to dereference
    val i: Int? = q?.length      // null
    val j: Int = q?.length ?: 0  // 0
}

fun todoTask7(client: Client?, message: String?, mailer: Mailer): Nothing = TODO(
    """
        Task 7.
        Rewrite JavaCode7.sendMessageToClient in Kotlin, using only one 'if' expression.
        Declarations of Client, PersonalInfo and Mailer are given below.
    """,
    documentation = doc7(),
    references = { JavaCode7().sendMessageToClient(client, message, mailer) }
)

fun sendMessageToClient(
        client: Client?, message: String?, mailer: Mailer
) {
    val send = { a: String,b: String -> mailer.sendMessage(a,b)}
    send.liftA2(client?.personalInfo?.email, message)
}

fun sendMessageToClient1(
        client: Client?, message: String?, mailer: Mailer
) {
    client?.personalInfo?.email.flatMap({ email ->
        message.map({ mailer.sendMessage(email, it)})
    })
}

fun sendMessageToClient2(
        client: Client?, message: String?, mailer: Mailer
) {
    val email = client?.personalInfo?.email
    if (email != null && message != null)
    {
        mailer.sendMessage(email, message)
    }
}

fun <A,B,C> ((A,B)->C).curry() : (A) -> (B) -> C {
    return { a : A -> { b : B -> this(a,b)}}
}

fun <A,B,C> ((A,B)-> C)?.liftA2(a : A?, b : B?) : C? {
    return this.flatMap { f -> a.flatMap({ aa -> b.map({ f(aa, it )})})}
}

fun <A,B> A?.map(f : (A) -> B) : B? {
  return this.flatMap({ f(it) })
}

fun <A,B> A?.flatMap(f : (A) -> B?) : B? {
    return if (this == null) null else f(this)
}

class Client (val personalInfo: PersonalInfo?)
class PersonalInfo (val email: String?)

interface Mailer {
    fun sendMessage(email: String, message: String)
}
