// initial-goal
!main.


// initial plan (triggered by the initial goal)
+!main <-

    // generates a random string
    R = generic/string/random( 12, "abcdefghijklmnopqrstuvwxyz");

    // calls actions and sends generated messages to "agent 0", agent names
    // are defined by the Java object / agent generator
     message/send("agent 0", R)

.


/**
 * receive plan which is execute iif a message is received,
 * plan trigger-literal is defined by the send action
 *
 * @param Message received message
 * @param AgentName name of the sending agent
 */
+!message/receive(  message( Message ), from( AgentName )  ) <-

    // variable MyName is set by the variable builder, Cycle is set by default, other variables will be unified
    generic/print( MyName, " received message [", Message, "] from [", AgentName, "] in cycle [", Cycle, "]")

.
