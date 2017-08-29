<!-- $theme: default -->
<!-- $size: 16:9 -->

# Course & Simulation Lab 
## _Modelling and simulation of socio-technical systems_

Prof. Dr. Jörg P. Müller, Dipl. Inf. Philipp Kraus 
TU Clausthal
September 19-20, 2017

---

## Course agenda overview
<table style="margin-left:133.46pt; width: 600px;" class="auto-style3">

<tbody>

<tr style="height:41pt">

<td style="border-style: solid; width:211pt; height: 30pt;" bgcolor="#F1F1F1" class="auto-style1">

Tuesday,

September 19

</td>

<td style="border-style: solid; width:210pt; height: 30pt;" bgcolor="#F1F1F1" class="auto-style1">

Wednesday,

September 20

</td>

</tr>

<tr style="height:104pt">

<td style="border-style: solid; border-width: 1pt; width:211pt; height: 50pt;" bgcolor="#FCE9D9" class="auto-style2">

<strong>10:00-12:00

</strong><br />
Lecture 1

Intelligent agents and multi-agent systems: An introduction

</td>

<td style="border-style: solid; border-width: 1pt; width:210pt; height: 50pt;" bgcolor="#DCE6F0" class="auto-style2">

<strong>9:00-12:00

<br />
</strong>Simulation Lab 2

Multi-agent-based modelling with LightJason

</td>

</tr>

<tr style="height:35pt">

<td style="width:211pt;border-top-style:solid;border-top-width:1pt;border-left-style:solid;border-left-width:1pt;border-bottom-style:solid;border-bottom-width:1pt;border-right-style:solid;border-right-width:1pt" bgcolor="#EBF0DE" class="auto-style2">

<strong>12:00-13:15

<br />
</strong>Lunch Break

</td>

<td style="width:210pt;border-top-style:solid;border-top-width:1pt;border-left-style:solid;border-left-width:1pt;border-bottom-style:solid;border-bottom-width:1pt;border-right-style:solid;border-right-width:1pt" bgcolor="#EBF0DE" class="auto-style2">

<strong>12:00-13:15

<br />
</strong>Lunch Break

</td>

</tr>

<tr style="height:87pt">

<td style="border-style: solid; border-width: 1pt; width:211pt; height: 50pt;" bgcolor="#DCE6F0" class="auto-style2">

<strong>13:15-15:00

<br />
</strong>Simulation Lab 1

Multi-agent-based Modelling with

LightJason

</td>

<td style="border-style: solid; border-width: 1pt; width:210pt; height: 50pt;" bgcolor="#FCE9D9" class="auto-style2">

<strong>13:15-16:00

<br />
</strong>Lecture 3

Algorithmic

mechanism design

</td>

</tr>

<tr style="height:35pt">

<td style="width:211pt;border-top-style:solid;border-top-width:1pt;border-left-style:solid;border-left-width:1pt;border-bottom-style:solid;border-bottom-width:1pt;border-right-style:solid;border-right-width:1pt" bgcolor="#EBF0DE" class="auto-style2">

<strong>15:00-15:15

<br />
</strong>Coffee Break

</td>

<td style="width:210pt;border-top-style:solid;border-top-width:1pt;border-left-style:solid;border-left-width:1pt;border-bottom-style:solid;border-bottom-width:1pt;border-right-style:solid;border-right-width:1pt" bgcolor="#EBF0DE" class="auto-style2">

<strong>16:00-16:15

<br />
</strong>Coffee Break

</td>

</tr>

<tr style="height:87pt">

<td style="border-style: solid; border-width: 1pt; width:211pt; height: 50pt;" bgcolor="#FCE9D9" class="auto-style2">

<strong>15:15-17:00

<br />
</strong>Lecture 2

Coordination and game theory

</td>

<td style="border-style: solid; border-width: 1pt; width:210pt; height: 50pt;" bgcolor="#F1F1F1" class="auto-style2">

<strong>16:15-17:00

<br />
</strong>Plenary Feedback and Wrap-Up

</td>

</tr>

</tbody>

</table>



<span>![image](sociotechnicalsystems2/image_003.png)</span>

---  

## Lecture 2

MULTI-AGENT COORDINATION: RATIONAL

INTERACTION


---  

## Outline Lecture 2

*   Basic concepts: Communication, Interaction, Coordination, Cooperation

*   Speech act theory

*   Agent types: Benevolent vs. self-interested

*   Game-theoretic model of rational interaction

    *   Routing and other games
    *   Models
    *   Examples

---
        
## Multi-agent system (MAS): A system, in which a number of agents ...

*   interact via communication
*   perform actions in a shared environment
*   have different spheres of influence
*   are linked via social or organizational relationships
<span>![image](sociotechnicalsystems2/image_006.jpg)</span>
*   Successful interaction requires the capability to cooperate with each other, to coordinate, or to negotiate
*   Transferring this statement to the traffic domain is obvious
    
---  
    
## MAS Context

*   Multi-agent systems (MAS) = Metaphor for describing, analysing, and engineering decentral organized systems

*   In multi-agent systems, we address questions such as:
    *   How can cooperation emerge in societies of self-interested agents?
    *   What kinds of languages can agents use to communicate?
    *   How can self-interested agents recognize a conflict, and how can they (nevertheless) reach an agreement?
    *   How can autonomous agents coordinate their activities so as to cooperatively achieve goals?

*   What distinguishes the multi-agent systems field is that it emphasizes that the agents in question are __computational, information processing__ entities.

---  
    
## Communication versus computation

*   Communication is generally more expensive and less reliable
    *   recomputing is often faster than requesting information over a communication channel
    *   communication can lead to prolonged negotiation
    *   chains of belief and goal updates caused by communication may not terminate
*   Communication in most cases achieves better quality
    *   information cannot always be reconstructed locally
    *   communication can be avoided only when the agents are set up to share all necessary knowledge. This is a very limiting assumption which cannot be practically achieved in most interesting cases
*   Deciding whether and what to communicate is often crucial and involves trade-offs...

---  
    
## Coordination, cooperation, and collaboration

*   **Coordination**: Avoid unnecessary activity

    *   reduce resource contention

    *   avoid livelock

    *   avoid deadlock

    *   maintain safety conditions

*   **Cooperation** is coordination among non-antagonistic agents

*   **Collaboration** is cooperative work on a joint goal, task, or
---


## Coordination, cooperation, collaboration?

<span>![image](sociotechnicalsystems2/image_012.png)</span>
<sup><sup>[Source: Picture 1.-3\. http://www.utdallas.edu/~mspong/Presentations/Montreal06.ppt](http://www.utdallas.edu/%7Emspong/Presentations/Montreal06.ppt), Picture 4. JP Müller, 1996</sup></sup>

---  


## Interaction often involves communication

*   The primary reason for communication among agents is to coordinate activities
*   Agents may coordinate without communication, provided that they have models of the others' behaviour, social norms, or other common knowledge
*   Communication involves the dimensions of
    *   who
    *   what
    *   when
    *   how (resources and protocol)
    *   why
*   To facilitate cooperation, agents often need to communicate their intentions (plans), goals, results, and state

    ---  
    
## Theoretical approach: Speech act theory <sup><sup>(Austin 61, Searle 69)</sup></sup>
*   Basic assumption: communication = action
*   Initially meant for natural language
*   Three aspects of a message:
    *   locution:
        *   how is the message phrased?
        *   e.g., **"it is hot here"**, or **"turn on the AC"**
    *   illocution:
        *   how is the message meant by the sender or understood by the receiver?
        *   e.g., a request to turn on the AC, or an assertion about the temperature
    *   perlocution:
        *   how does the message influence the receiverĂ˘ÂÂs behaviour
        *   e.g., turn on the AC, opens the window, nods her head, ignores the speaker

---  
## Speech-act-based communication in AgentSpeak

Agents can send messages using .send() action
    <span style=" color: #33C;">.send(rcvr, type, content)</span>
Sends message to agent to agent with name "<span style=" color: #33C;">rcvr</span>",
    message type (performative) "<span style=" color: #33C;">type</span>" and content "<span style=" color: #33C;">content</span>"
    
Examples:
    <span style=" color: #33C;">.send(car1, tell, isfree(pos(3,6))</span> 
-> Agent <span style=" color: #2C2CB8;">car1</span> adds belief <span style=" color: #2C2CB8;">isfree(pos(3,6))</span> to its belief base
    <span style=" color: #33C;">.send(car1, achieve, keepto(right))</span> 
-> Agent car1 adds event (goal) <span style=" color: #2C2CB8;">+!keepto(right)</span> to its goal base

Sender of a message is always saved by the recipient as an annotation to the content


---  
## IEEE FIPA Standards Body (www.fipa.org)

*   FIPA = Foundation of Intelligent Physical Agents
*   Founded in 1996 aiming at standardizing of agent-based Software
*   Scope of standardization
    *   Agent Management<span class="s7">: runtime architecture of multi-agent systems</span> (MAS) including components and services
    *   Agent communication<span class="s7">: Interaction protocols, speech acts, content</span> languages
    *   Agent Message Transport<span class="s7">: Low-level message transport</span>
 FIPA Agent Communication Language (ACL),influenced by KQML <sup><sup>[(http://www.fipa.org/repository/aclspecs.html)](http://www.fipa.org/repository/aclspecs.html))
	        

---  
        
## MAS (interaction) protocols
*   A MAS protocol describes and restricts admissible courses of interaction processes (typically, message sequences)
*   A MAS protocol is defined at the knowledge level
    *   above the ISO/OSI transport layer 
    *   involves high-level concepts, such as
        *   commitments, beliefs, intentions
        *   permissions, responsibilities, requests
*   Idea: Standardize the meaning of message types to solve problem of the message semantics
*   Standards body IEEE FIPA has standardized a number of message types and interaction protocols

    ---  
## Example: FIPA-Interaction protocol for task/goal delegation

<span>![image](sociotechnicalsystems2/image_012.png)</span>
 

---  
## Multi-agent system types

The literature often distinguishes two types of systems in the area of MAS
**Sellf-interested agents**
*   Existence of a global goal/problem is not required
*   Agents co-exist and pursue local goals in a self-interested fashion, independent from each other
*   Often: assumption of _(bounded) individual rationality_
*   Interaction/coordination occurs through a shared environment: Avoid/resolve resource conflicts; exploit synergies

Benevolent agents: Distributed Problem Solving
*   A global, shared problem; often: global utility function
*   Agents differ by capabilities or knowledge ÄÂÂ  no single agent can solve the problem on its own
*   Goal: Find a good or optimal solution to the problem by a group (or "team") of agents

---  

## Self-interested Agents

The benevolence assumption simplifies system design

However...

*   If agents represent individuals or organizations, we cannot make the benevolence assumption

*   Agents will be assumed to act further to their own interests, possibly at expense of others

*   Potential for _conflict_

*   May complicate the design task enormously


---

## Two decision problems

*   Routing: Navigation system announces a congestion ahead and suggests a deviation
<span>![image](sociotechnicalsystems2/image_025.png)</span>
    *   What should you do; stay on the road or take the deviation?

    *   What is better depends on your fellow drivers' decisions...

*   Traffic planners: Should we build a new clearway to reduce overall travel times from S to E?

<span>![image](sociotechnicalsystems2/image_026.png)</span>


---  
    
## Braess Paradox: New roads not always help Ă˘ÂĹ

*   Adding a new road with high capacity
<span>![image](sociotechnicalsystems2/image_029.png)</span>

*   Consider optimal "equal split" solution (SAE/SBE)
*   There is a route with shorter travel time: SABE
*   Drivers that have information about travel times change to route SABE ->Â  destroy optimal flow

 _**By adding a road, travel time increases!!!**_

---  

## Couldn't agents coordinate with each other to resolve this?

*   Assume that the cars agree to avoid the new road and to distribute equally over the old routes
*   We did already show that faster route exists (travel time 70 instead of 83)
    ->Â <span class="p">There is a huge incentive for agents to defect</span>
    ->Â <span class="p">This solution is unstable for egoistic agents</span>
*   What can be done?


---  

## Example: Traffic routing

*   Public transport companies $$TC_1 and $$TC_2 of neighbouring countries Clausland and Zellerreich have agreed to route passengers "seamlessly"
*   Both own/operate their local networks
*   In routing traffic originating in one network with destination in the other, routing choice is made by originating network's TC
*   Routing choices made by one TC may affect load and cost of the other!
     <span>![image](sociotechnicalsystems2/image_032.png)</span>
---

## Example: Traffic routing (2)

<span>![image](sociotechnicalsystems2/image_032.png)</span>

*   $$C, $$S are boundary nodes through which traffic must go
*   Traffic streams to be routed: $$o_1 -> $$d_1 and $$o_2 -> $$d_2
*   Assume each edge has cost 1 associatedz
*   Each TC pays for cost occouring on its own network
*   Cost from $$C to $$d_1 and $d_2 is assumed to be 0
*   How should $$TC_1 and $$TC_2 route their traffic streams?

---  
    
## Game theory

*   The examples describe scenarios of interaction between self-interested players ("agents")
*   Game theory "aims to model situations in which multiple participants interact or affect each other's outcomes" (Nisan et al., 2007, p.3)
*   In particular, game theory deals with the formulation of optimal strategies for solving conflicts
*   Seminal work on game theory
    <sup><sup> J.  von Neumann und O. Morgenstern. The Theory of Games and Economic Behaviour. Princeton University Press, Princeton, NJ (1944).</sup>
*   All the examples described above can be modelled as one-shot simultaneous move games
*   Before we show that, we introduce the basic concepts
    

    
---
    
## Game-theoretic model of rational multi-agent interaction

*   Simple case: two agents only $$(|Ag| = 2, Ag = {i, j})
*   Interaction of agents produce results
*   The environment in our MAS is described through a set of result states
	*   $$Omega = { $$omega_1, $$omega_2, âŚ} 

*   Assumption: Agents are self-interested, i.e., they have (individual) preferences over result states

*   Preferences can be represented by assigning utility (payoff) values to states


---  

## Utility and preferences

*   We represent preferences through utility functions:
    
    $$u_i: $$Omega $$rightarrow $\mathfrak{R}
    
    $$u_j: $$Omega $$rightarrow $\mathfrak{R}
    
*   Utility functions induce preference orderings over the result states of interactions
	
    $$omega $$leq_i $$omega' means $$u_i($$omega) $$leq $$u_i($$omega')
    $$omega $$l_i $$omega' means $$u_i($$omega) $$l $$u_i($$omega')
      

*   Interpretation of utility
	*   Not the same as money
	*   But useful analogy

<span>![image](sociotechnicalsystems2/image_038.jpg)</span>

---  

## Multi-agent encounters

We need a model of the environment in which these agents will act...
*   agents simultaneously choose an action to perform, and as a result of the actions they select, an outcome in W will result
*   the actual outcome depends on the combination of actions
*   assume each agent has just two possible actions that it can perform, C ("cooperate") and D ("defect") Environment behaviour given by state transformer function:

    <span>![image](sociotechnicalsystems2/image_040.jpg)</span>


---  
    
## State transformer functions

*   Some examples

<span>![image](sociotechnicalsystems2/image_030.png)</span>


---  


## Rational choice

**FORMELN**

*   Relevant: Environments that can be influenced by both agents consider following utility functions
<span>![image](sociotechnicalsystems2/image_064.png)</span>

*   Simplified notation

   
*   Then agent <span class="s34">i</span> Ă˘ÂÂs preferences are:

"_C_" is the individual _rational choice_ for _i_.
(Because _i_ prefers all outcomes that arise through _C_ over all outcomes that arise through _D_.)

    

---  


## Representation as payoff matrix

*   We can characterize the previous scenario in a payoff matrix
<span>![image](sociotechnicalsystems2/image_045.png)</span>

*   We call _i column_ player, and _j row_ player
*   We call D and C _strategies_ that the players may choose


---  
    
## Dominant strategies

*   Given any particular strategy (either _C_ or _D_) of agent _i_, there will be a number of possible outcomes

*   We say $$s_1 _dominates_ $$s_2 if every outcome possible by _i_ playing $$s_1 is preferred over every outcome possible by_i_ playing $$s_2
*   A rational agent will never play a dominated strategy
*   So, in deciding what to do, we can _delete dominated strategies_
*   Unfortunately, there isn't always a unique undominated strategy

---  

## Routing games (Example 1 reconsidered)

*   Players: TC1 and TC2 simultaneously route one unit of traffic
*   Strategies: Route via S _or_ Route via C
*   Dilemma: one is better from a selfish perspective, but will hurt the other player
*   Payoff matrix (figures are cost, so less is better!)
<span>![image](sociotechnicalsystems2/image_044.png)</span>

---  


## Analysis of the routing game

<span>![image](sociotechnicalsystems2/image_055.png)</span>

*   Are there dominant strategies for any player?
*   Is there a (collectively) optimal strategy pair?
*   What strategy would a rational player choose?
*   Is there a stable setting, where no player has an incentive to change their strategies?

    

---  
    
## Nash Equilibrium

*   In general, we will say that two strategies $$s_1 and $$s_2 are in Nash equilibrium if

1.    under the assumption that agent _i_ plays $$s_1, agent _j_ can do no better than play $$s_2; and
2.    under the assumption that agent _j_ plays $$s_2, agent i can do no better than play $$s_1.
*    _Neither agent has any incentive to deviate from a Nash equilibrium_
*    This is desirable as modifying strategies requires effort (cost!) and can endanger system stability
*    Unfortunately:
1.   _Not every interaction scenario has a Nash equilibrium_
2.   _Some interaction scenarios have more than one Nash equilibrium_


---

## Does the routing game have Nash equilibria?

<span>![image](sociotechnicalsystems2/image_055.png)</span>

    
---

## Analysis of the routing game (2)

<span>![image](sociotechnicalsystems2/image_055.png)</span>

*   Note: The routing game is equivalent to the famous prisoner's dilemma!
*   Intuitively, cooperation is the best choice; But it is rational for the agents not to cooperate
*   Does this imply that cooperation is sometimes unreachable in societies of self-interested agents? Humans??
*   Can we recover cooperation?

---  


## Counter-arguments

### Conclusions that some have drawn from this analysis:

*   the game theory notion of rational action is wrong!

*   somehow the dilemma is being formulated wrongly

### Arguments to recover cooperation

*   We are not all Machiavelli!

*   The other prisoner is my twin!

*   The shadow of the future... _
    _if interaction is repeated, uncooperative behaviour is punished_



---  


## Iterated Routing Game (aka Prisoner's Dilemma)

*   One answer: play the game more than once
*   If you know you will be meeting your opponent again, then the incentive to defect (=not to cooperate) appears to evaporate
*   Cooperation is the rational choice in the infinitely repeated prisoner's dilemma :-)
*   For the prisoner's dilemma with n rounds, n $$\leq 1 finite, pre-determined, commonly known, defection is the best strategy :-(


---

## Example: Routing congestion
*	Assume that two traffic streams T1 and T2 originate at node o and need to be routed into the Zellerreich traffic network
*	T1 and T2 routed by different authorities/companies A and B
*	The two crossing-points C and S can be used; note that S is slightly closer from o than C
*	C and S tend to congest easily, so if A and B send their streams through the same crossing point, extra delay will occur
*	What should A and B do? 
<span>![image](sociotechnicalsystems2/image_053.png)</span>

---

## The routing congestion game

*	Players: A and B simultaneously route one unit of traffic 
*	Strategies: Route via S or Route via C
*	Assume congestion cost of 3, plus cost of edge
*	Good results will require players to choose different routes
*	Possible payoff matrix (figures are cost, so less is better!)
*	_Are there Nash equilibria? What can you say about them?_
<span>![image](sociotechnicalsystems2/image_050.png)</span>

---

## N-player game: Investment in pollution reduction
N countries need to decide on whether or not to take extra efforts by passing a legislation to reduce pollution
Doing so will cost the country 3 billion âŹ
Not doing so will cause cost of 1 billion âŹ to each country (because of pollution)
*   What should the governments of the countries do?

<span>![image](sociotechnicalsystems2/image_059.gif)</span>
<sup><sup>    [http://www.worldatlas.com/webimage/countrys/eunewneb.gif](http://www.worldatlas.com/webimage/countrys/eunewneb.gif)</sup></sup>
Is there a rational solution?
Is there an optimal solution?
Is there a stable solution?
    
---

## The pollution game

<span>![image](sociotechnicalsystems2/image_061.gif)</span>
  
*   Players: the n countries simultaneously decide on whether to pass policy to reduce pollution
*   Strategies: YES = Cooperate, NO = Defect
*   Assume k out of n countries choose not to control pollution
    -> The cost incurred by each of the defecting countries is k
    -> The cost incurred by each of the n-k cooperating countries is k+3

*   _Does the game have Nash equilibria?_
*   _(How) does this game relate to any of the games we have previously seem?_


--- 
    
## Tragedy of the commons

<span>![image](sociotechnicalsystems2/image_065.png)</span>

*   N parties have access to a shared resource

    *  E.g. logistics companies that send lorries through a road segment with known maximum capacity _c_, say _c_=1

*   Each party wants a large fragment of the road capacity, but:
*   Service quality deteriorates as overall assigned load increases


---  


## Tragedy of the commons: formulation as a game

*   Each players' optimal selfish strategy depends on what other players do
*   Infinite number of strategies for each player:
	__Use đĽ_đ fragment of road capacity, đĽ_đâ[0,1]__
*	Define players' payoff:
	* If $$â_đ $$x_j âĽ 1 (total traffic exceeds capacity):
	-> payoff for all players j is	0 	
	* If $$â_đ $$x_j <1
	-> payoff for player i is $$đĽ_đâ(1â%%â_đ â $$đĽ_đ )

*   This definition reflects the intended trade-off
    *  Players want to use as much of the resource as possible
    *  Over-usage is bad for everyone
*   Now what should self-interested players do?

---  


## Tragedy of the commons game: stable strategies

*   We consider player i
*   Assume: other players j use _t_ = $$â_(đâ đ) $$đĽ_đ <1
*   Optimization problem for player i: đĽâ(1âđĄâđĽ)âđđđĽ

*   Solving it reveals đĽ=(1âđĄ)/2 
	(see next slide for some high school maths)

*   A set of strategies is stable if all players are playing their optimal selfish strategy, given the strategies of all other players

*   For our case, that means:
		đĽ_đ=(1â $$â_(đâ đ) $$đĽ_đ )/2, which has as unique solution:
		đĽ_đ=1/(đ+1) for all i

---

## How to solve the optimization problem

**FORMEL**
   đĽâ(1âđĄâđĽ)âđđđĽ
<-> âđĽ^2â (đĄâ1)âđĽâđđđĽ

Compute the zeros of the quadratic function, e.g. by using the pq formula:

Zeros of  đŚ=$$đĽ^2+đâđĽ+đ  are at  âđ/2Âąâ((đ/2)^2âđ)

Transform equation âđĽ^2â (đĄâ1)âđĽ=0 to above form:
-> đĽ^2+ (đĄâ1)âđĽ=0
-> With p=1, q=0 the pq formula delivers
-> đĽ_1=0,  đĽ_2=(1âđĄ)
-> The function has its maximum at (1âđĄ)/2

---  
    
## But why is it called _tragedy_?

*   It is a tragedy, because the resulting total payoff is extremely    low
With $$đĽ_đ=1/(đ+1), we can calculate each playerâs payoff as $$đĽ_đâ(1â$$â_(đâ đ) $$đĽ_đ )=$$1/(đ+1)^2 
   
*	This means that the sum of all playersâ payoffs is $$đ/(đ+1)^2 â1/đ
Compare if the overall used fraction of the resource capacity was $$â_đ $$đĽ_đ =1/2 , the sum of all playersâ payoffs were 1/4 and hence approximately  đ/4 times bigger

*	In this game, the n users sharing the common resource _overuse it so that the total value of the shared resource decreases quite dramatically_

---  
    
    
## The "price of anarchy"

*   How much do we lose by selfish behaviour?
*   How inefficient are equilibria reached by selfish rational agents ?
    
    _"in comparison to an idealized situation in which the agents would strive to collaborate selflessly with the common goal of minimizing total cost"?_ <sup><sup> (Nisan et al, 2007, p. xiv)</sup></sup>
*   The price of anarchy depends on the design of the game
*   Examples: Tragedy of Commons, prisoner's dilemma
*   Note that the contrary of anarchy is __dictatorial__ behaviour!
*   It is often found that the price of anarchy is not nearly as expensive as one may think (e.g. max. 33% to optimal solution for some routing games) <sup><sup>[Roughgarden]</sup></sup>


---


## Mixed-strategy games

*   Up to know, we have seen "pure" strategies, i.e. a strategy was always to pick one alternative deterministically

*   Now look at the game theory version of Rock, Paper, Scissors
*   Analysis
    *   Dominant Strategy?
    *   Nash equilibrium?
*   What strategy should a player play in this game?

    <span>![image](sociotechnicalsystems2/image_089.png)</span>


---


## Mixed strategy (Wooldridge 2009, 260f)

*   Mixed strategy
    *   Choice of actions based on probabilities
*   Formally:
    *   Let S = {s1, s2, âŚ, sk } be possible actions
    *   A mixed strategy for player i is a probability distribution Pi=[$$p_i1, $$p_i2, âŚ, $$p_ik] over S
	*	Meaning: i plays s1 with probability $p_11, $$s_2 with probability $p_12, etc.

*   Apply to rock, stone, scissors
    *   "Choose randomly between rock, stone, scissors, where each alternative is chosen with equal probability"
    *   Note: This mixed strategy is in a Nash Equilibrium with itself


---  


## Nash equilibria for mixed strategies
![image](sociotechnicalsystems2/image_091.jpg#floatright  max-height: 20em)
<sup>Peter Badge/Typos 1</sup> 

Theorem(w/o. proof):
*   Each game, in which each player has a finite set of possible strategies, has a mixed-strategy Nash equilibrium
*   For this insight, John Forbes Nash Jr. won the Nobel prize in economic sciences in 1994 (with Reinhard Selten and John Harsanyi)



--- 

## Game theory - possibilities and limitations

*   Powerful analytic instrument
*   Algorithmic game theory provides means to compute (if existent) strategies and equilibria
*   Broad spectrum of different game types and variants
*   Algorithms for finding Nash equilibria
	*   <sup>(Lemke and Howson, 1964)</sup> for two player games
    *   Search-based algorithm <sup>(Porter et al., 2004)</sup> for n-player games
*   Computational complexity
	*   Finding a Nash equilibrium is a combinatorial problem
    *   The Nash Problem is PPAD-complete <sup>(Papadimitriou, 1994)</sup>
*   Recently, specific classes of games have been successfully used to engineer e.g. surveillance schedules <sup>(Yang et al., AAMAS 2014)</sup>

---  


## References for this Section

**J. L. Austin (1962).** _How to Do Things With Words_. Oxford University Press: Oxford, England.
**R. Axelrod (1984).** _The Evolution of Cooperation_. Basic Books: New York.
**K. Fischer, N. Kuhn, and J. P. MĂźller (1994).** _Distributed, knowledge-based, reactive scheduling in the transportation domain_. In Proceedings of the Tenth IEEE Conference on Artificial Intelligence and Applications, pages 47Ă˘ÂÂ53, San Antonio, Texas, March 1994.
**N.R. Jennings, K. Sycara, and M.J.Wooldridge (1998).** _A Roadmap of Agent Research and Development._ In Journal of Autonomous Agents and Multi-Agent Systems. 1(1), pages 7-36\. July 1998.
**C. Lemke and J. Howson (1964).** Equilibrium points of bimatrix games. Journal of the Society for Industrial and Applied Mathematics, 12, 1964.
**N. Nisan, T. Roughgarden, E. Tardos, V. V. Vazirani, eds (2007).** _Algorithmic Game_Theory<span class="s81">. Cambridge University Press: Cambridge, England.</span>

---  


## References for this Section

**R. Porter, E. Nudelman, Y. Shoham (2004).** _Simple search methods for finding a Nash equilibrium._ In _Proceedings of the 19th national conference on Artifical intelligence_ (AAAIĂ˘ 04), Anthony G. Cohn (Ed.). AAAI Press 664-669.
**J. R. Searle (1969).** _Speech Acts: An Essay in the Philosophy of Language_. Cambridge University Press: Cambridge, England.
**M. J. Wooldridge.** An introduction to Multi-agent Systems. John Wiley & Sons, 2nd edition 2009.
**R. Yang, B. Ford, M. Tambe, A. Lemieux (2014).** _Adaptive Resource Allocation for Wildlife Protection against Illegal Poachers._ International Conference on Autonomous Agents and Multi-agent Systems (AAMAS), p.453-460.