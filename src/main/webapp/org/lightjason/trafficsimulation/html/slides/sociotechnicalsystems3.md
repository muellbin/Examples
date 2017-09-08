<!-- $theme: default -->
<!-- $size: 16:9 -->

## Course & Simulation Lab 
## _Modelling and simulation of socio-technical systems_

* Jörg P. Müller, Philipp Kraus 
* TU Clausthal
* September 19-20, 2017


---
<!-- page_number: true -->
## Course agenda overview

|                                   Tuesday, September 19                                          |                            Wednesday, September 20                                    |
|:------------------------------------------------------------------------------------------------:|:-------------------------------------------------------------------------------------:|
| **10:00-12:00**<br/>**Lecture 1**<br/>Intelligent agents and Multi-agent systems:An introduction | **9:00-12:00**<br/>**Simulation Lab 2**<br/>Multi-agent-based modeling withLightJason |
| **12:00-13:15**<br/>**Lunch Break**                                                              | **12:00-13:15**<br/>**Lunch Break**                                                   |
| **13:15-15:00**<br/>**Simulation Lab 1**<br/>Multi-agent-based modeling with LightJason          | **13:15-16:00**<br/>**Lecture 3**<br/>Algorithmic mechanism design                    |
| **15:00-15:15**<br/>**Coffee Break**                                                             | **16:00-16:15**<br/>**Coffee Break**                                                  |
| **15:15-17:00**<br/>**Lecture 2**<br/>Coordination and game theory                               | **16:15-17:00**<br/>Plenary Feedback and Wrap-Up                                      |
    
    
---
## Lecture 3

_MECHANISMS FOR COLLECTIVE DECISION-MAKING_

*   **Collective decision-making**
*   **Voting mechanisms**


---
## Collective decision processes

*   We consider collective decision processes: How can multiple self-interested agents find an agreement on a matter?
*   Different scenarios can be considered:
    *   Agreeing on (fairly) allocating/dividing a shared resource (e.g., auction)
    *   _Agreeing on an alternative through voting (e.g., political election)_
    *   Agreeing on allocating and solving a common task (who is doing what?)
    *   Agreeing on a judgement of opinion (judgment aggregation), e.g. by argumentation
*   Note: We focus on collective decision-making of _automated agents_


---
## Computational Social Choice Theory

*   Social Choice Theory <sup><sup>(Arrow et al., 2002)</sup></sup> is the study of mechanisms for collective decision making
*   Usually considered part of economic theory, with contributions also from political science, sociology, philosophy, and maths
*   __Computational Social Choice__ <span class="p"> <sup><sup>(Brandt et al., 2013)</sup></sup>: Applies concepts of Social Choice Theory to automated collective decision processes in the field of autonomous agents and multiagent systems.</span>
*   Related to game theory and decision theory


---
## General model: agents, environments, interaction, protocols, mechanisms

![image](slides/sociotechnicalsystems3/image_003.png#centering)


--- 
## Mechanism design

*   Interactions between <u>agents</u> are described and enforced by a <u>mechanism</u>
*   The mechanism defines the rules of encounters among agents (e.g. auction, voting, game, or exchange market)
*   <u>Protocols</u> <span class="p">describe how agents interact with each other and with components implementing the mechanism (e.g., an auctioneer)</span>
*   The field of mechanism design investigates how mechanisms should be defined to support certain desirable properties
*   We look at some desirable properties of mechanisms


---
## Properties of mechanisms (and their protocols)

*   __Convergence__: Does the mechanism guarantee successful completion of the interaction?
*   __Maximize social welfare__: Does the mechanism maximize the aggregated utilities of the participating agents?
*   __Pareto efficiency__: Does the mechanism guarantee a solution that no agent can improve for itself without reducing the utility of at least one other agent?
*   __Individual rationality__: Is it the rational choice of all agents to follow the rules given by the mechanism?</span>
*   __Stability__: Does the mechanisms provide all agents an incentive to behave in a certain (desirable) manner (e.g. Nash equilibrium)?</span>


---
## More properties of mechanisms

*   __Simplicity__<span class="p">: Does the mechanism make it easy for agents to find an optimal (or appropriate) strategy?</span>
*   __Efficiency of communication__<span class="p">: Does the mechanism support finding a solution with low communication effort?
*   __Robustness__<span class="p">: Is the mechanism tolerant with respect to the failure of individual agents? (e.g., no "single point of failure")
*   __Symmetry, fairness__<span class="p">: Does the protocol treat all participants equally or does it intrinsically favour certain agents?</span>


---
## Collective decision-making through voting

*   Voting is an often used class of mechanisms for collective decision-making
*   Individual agents of a group have differing preferences over possible outcomes
*   A central instance knows these preferences, or agents reveal their preferences truthfully (assumption!)
*   A <span class="s11">__social choice function__</span> aggregates the preferences and makes a decision
*   This decision is considered binding for/by all agents of the group


---
## Social choice rule vs. Social choice function (1)

![image](slides/sociotechnicalsystems3/image_004.png#floatright)
<sup>Adapted from (Wooldridge, 2009, p. 285)</sup>

$$Ag={1,...,n}$$ 
set of agents
$$\Omega = { \omega , \omega ' , ... }$$
possible outcomes

---
## Social choice rule vs. Social choice function (2)
Preference ordering for each voter i, e.g.
$$\omega_2 \succ  \omega_1 \succ  \omega_3$$


---
## Selected voting procedures

*   Plurality voting
*   Sequential majority elections, aka Binary voting
    *   Outcomes ''play'' tournament of pairwise comparisons
*   Borda count voting
*   __Slater ranking__


---
## Plurality voting

*   Every voter submits preference order
*   We count how many times each outcome is ranked first
*   Winner is the outcome with the highest count
*   For two outcomes: majority voting!
*   Advantages:
    *   Easy to implement
    *   Easy to understand for voters
*   Anomalous properties can be observed when more than two candidates


---
## Plurality voting: Political elections example I (1)

*   Consider elections with three parties 
$$c(du), \ s(pd), \ and \ f(dp)$$

Candidates 
$$\omega_c, \omega_s, \omega_f$$



---
## Plurality voting: Political elections example I (2)

*   We consider three voter groups with similar preferences:

Conventionally left-wing (38%): 
$$\omega_s \succ  \omega_f \succ  \omega_c$$
Social liberal (18%): 
$$\omega_f \succ  \omega_s \succ  \omega_c$$
Conservative right-wing (44%): 
$$\omega_c \succ  \omega_f \succ  \omega_s$$


---
## Plurality voting: Political elections example II

*   Plurality voting
    *   CDU wins

But: For 56% of the voters,
$$\omega_c $$
was the least preferred candidate

*   Consequence:
	*   FDP voters are likely to vote for SPD candidate
    *   Strategically misrepresent preferences in order to bring about a more preferred outcome

---
## Plurality voting: Condorcet paradox I

*   Suppose you have three candidates 
$$\omega_1, \omega_2, \omega_3$$ 
and three voters, 
$$Ag = {1,2,3}$$ 
with preferences
$$\omega_1 \succ_1  \omega_2 \succ_1  \omega_3$$
$$\omega_3 \succ_2  \omega_1 \succ_2  \omega_2$$
$$\omega_2 \succ_3  \omega_3 \succ_3  \omega_1$$
 

---
## Plurality voting: Condorcet paradox II (1)

*   Observations for plurality voting
    *   outcome is tied - no winner
    *   Suppose, we select:
$$\omega_1$$
2/3 of the voters would prefer 
$$\omega_3 \ over \ \omega_1 !$$

---
## Plurality voting: Condorcet paradox II (2)
*   Suppose, we select:
$$\omega_3$$
2/3 of the voters would prefer 
$$\omega_2 \ \omega_1 \ !$$

---
## Plurality voting: Condorcet paradox II (3)
*   Suppose, we select:
$$\omega_2$$
2/3 of the voters would prefer 
$$\omega_1 \ over \ \omega_2 \!$$
*   For every outcome, there is another candidate that is preferred by at least 2/3 of the candidates!
*   Paradox of the cyclic majorities


---
## Possible winner vs. Condorcet winner

*   An outcome is a _possible winner_ if there is some voting agenda which could result in that outcome being a winner
*   An outcome is called the _Condorcet winner_ if it is the overall winner for each agenda, i.e. if it would win a two-candidate election against each of the other outcomes in a plurality vote
*   Finding possible winners and Condorcet winner (if existent) are two important computational problems in Computational Social Choice theory
*   To solve them, we introduce the concept of a majority graph as a compact representation of preference structures


---
## Analysing preference structures: majority graph (1)

Directed graph
*   Nodes correspond to outcomes 
$$\omega \in \Omega$$
*   Edge from outcome 
$$\omega \ to \ \omega'$$
if a majority of voters rank  
$$\omega \ above \ \omega'$$
(in a direct competition)


---
## Analysing preference structures: majority graph (2)
*   Properties of majority graph
    *   Complete: For any two outcomes 
    $$\omega_i \ and \ \omega_j$$ 
either 
$$\omega_i$$
defeats 
$$\omega_j$$



---
## Analysing preference structures: majority graph (3)

or 
$$\omega_j$$ 
defeats 
$$\omega_i$$



---
## Analysing preference structures: majority graph (4)

*   Asymmetric: if 
$$\omega_i$$
defeats 
$$\omega_j,$$

$$\omega_j$$  
must not defeat 
$$\omega_i$$

*   Irreflexive: An outcome will never defeat itself
*   A graph with these properties is called "tournament"


---
## Majority graph: Usage and Examples (1)

*   A candidate 
$$\omega_i$$
is a possible winner, if for every other outcome 
$$\omega_j$$ 
there is an path from 
$$\omega_i \ to \ \omega_j$$


---
## Majority graph: Usage and Examples (2)
*   A candidate 
$$\omega_i$$
is a Condorcet winner, if there is an edge 
$$\omega_i$$ 
to every other edge in the graph



---
## Majority graph: Usage and Examples (3)

*   Left: Every outcome is a possible winner
*   Right: Outcome 
$$\omega_1$$
is a Condorcet winner

![image](slides/sociotechnicalsystems3/image_006.png#centering)


---
## Binary voting

*   Alternatives "play" against each other bilaterally in a K.O. tournament
*   The social choice function provides the tournament schedule and records the results makes the decision
*   The binary voting protocol has a number of disadvantages as the following example shows


---
## The schedule paradox

*   Let x, y, z be possible outcomes of the voting
*   Let the group of agents be divided in three subgroups
    * Group 1: x > z > y (35% of overall group size)
    * <span style=" color: #000;">Group 2: y > x > z (33%)
    * <span style=" color: #000;">Group 3: z > y > x (32%)
*   Consider the following three variants of the tournament schedule:

a) ![image](slides/sociotechnicalsystems3/image_008.png)
b) ![image](slides/sociotechnicalsystems3/image_009.png)
c) ![image](slides/sociotechnicalsystems3/image_010.png)

The result depends on how the schedule is designed


---
## In the binary protocol, irrelevant alternatives may cause problems

*   Assumption: Alternative z is irrelevant for the decision
*   By adding z as an alternative, the outcome may change
*   Consider cases b) and c) in the example on the previous slide
*   Z is irrelevant, because it wins in neither of the two cases
*   But:

If z first plays against 
$$x \rightarrow y$$
wins
If z first plays against 
$$y \rightarrow x $$
wins


---
## The pareto-dominated winner paradox

*   Let a, b, x, y be possible outcomes
*   Let the group of agents be divided into three (equally sized) subgroups:
    *   Group 1: x > y > b > a
    *   Group 2: a > x > y > b
    *   Group 3: b > a > x > y
    
![image](slides/sociotechnicalsystems3/image_011.png)
*   y wins even though everyone prefers x to y


---
## Borda Voting 1

*   The Borda mechanism attempts to resolve the problems observed for binary voting
*   Let 
$$x_1, ..., x_n$$
be the possible outcomes, then each agent scores its most-preferred outcomes n points, its second- most preferred outcome n-1 points, etc., and its least preferred outcome 1 point.
*   The sum of scores is calculated for all outcomes/agents
*   The outcome with the highest aggregated score wins


---
## Borda Voting 2

Example above:
    * X receives score of 3 * 0.35 + 2 * 0.33 + 1 * 0.32 = 2.03
    * Y receives score of 1.98
    * Z receives score of 1.99 -> _**Using Borda Voting, x wins**_


---
## Reversed-order paradox

*   Let a, b, c, x be possible outcomes
*   Let the agents be divided into the following (equally sized) groups:
    *   Group 1: x > c > b > a
    *   Group 2: a > x > c > b
    *   Group 3: b > a > x > c
    *   Group 4: x > c > b > a
    *   Group 5: a > x > c > b
    *   Group 6: b > a > x > c
    *   Group 7: x > c > b > a
*   Borda values are: x=22, a=17, b=16, c=15
*   If we remove outcome x, we get: c=15, b=14, a=13
    *   As c is always behind x, it doesn't lose points by removal of x


---
## Borda Voting is also sensitive to irrelevant alternatives

*   Let x, y, z be possible outcomes
*   Three groups of agents
    * <span style=" color: #000;">Group 1: x > z > y (35%)</span>
    * <span style=" color: #000;">Group 2: y > x > z (33%)</span>
    * <span style=" color: #000;">Group 3: z > y > x (32%)</span>
*   As described before, the winner using Borda Voting is x, i.e. z can be considered an irrelevant alternative
*   By removing z from the voting, y becomes the winner


---
## The majority winner's paradox

*   In some cases, Borda Voting violates the principle of the majority winner
*   Example: Consider seven groups of agents:
    *   Group 1: a > b > c
    *   Group 2: a > b > c
    *   Group 3: a > b > c
    *   Group 4: b > c > a
    *   Group 5: b > c > a
    *   Group 6: b > a > c
    *   Group 7: c > a > b
*   Winner according to the majority rule for every binary protocol: <span class="s11">**_a_**</span>
*   Borda values: <span class="s11">**_b_**</span>=16, a=15, c=11


---
## Is there a desirable way of aggregating preferences of multiple agents? (1)

Let 
$$\Omega$$
be a set of outcomes and let _A_ be a set of agents

Each agent 
$$\in A$$
has a (total) preference ordering 
$$R_i$$ 
over 
$$\Omega$$


---
## Is there a desirable way of aggregating preferences of multiple agents? (2)

*    Let 
$$R = (R_i,...,R_(|A|))$$
*   Let 
$$G(R,\Omega)$$ 
be the social choice function


---
## Desiderata for G (1)

G should:

*   _**satisfy the Pareto principle:**_
	*	if each agent prefers x over y, then 
$$G(R, \Omega)$$ 
also prefers x over y

*   **_be independent of irrelevant alternatives:_**
    *   **if** 
$$G(R,\Omega)$$
prefers x over y 


---
## Desiderata for G (1)

AND

$$R'$$

is another preference profile
    *   such that the preference value between x and y is the same in 
$$R'$$
as in 
$$R$$
for each agent


---
## Desiderata for G (2)

**then**  
$$G(R ',  \Omega)$$ 
also prefers x over y
*   _**be non-dictatorial**_: No single agent in A determines the preferences for every pair x,y in 
$$\Omega$$


---
## Arrow's impossibility theorem (in a nutshell)

For 
$$|\Omega| \geq 3,$$ 
there exists no social choice function G that satisfies all desiderata 1-3

*   Implication: every social choice function that satisfies 1\. and 2\. is dictatorial
*   What can we do? Which property can we do compromise on more easily? 1\. or 2.?
*   Other option: Change how voters express preferences
    *   Range Voting: candidates are rated on a scale, e.g. 0 ... 100
    *   Approval voting: binary rating; 1 = acceptable, 0 = not acceptable


---
## Using voting in traffic applications

*   We end this lecture by looking at a voting-based approach for vehicle platoon formation and routing in traffic applications
*   This work was done by Sophie Dennisen in the 1st cohort of SocialCars. For details, contact Sophie or look at the publications:

<sup><sup><sup> **Baumeister, D.; Dennisen, S.; Rey, L. (2015)**. Winner Determination and Manipulation in Minisum and Minimax Committee Elections. In Proc. 4th International Conference on Algorithmic Decision Theory (ADT 2015), vol. 9346 of Lecture Notes in Artificial Intelligence, 469-485, Springer-Verlag.
**Dennisen S.; MĂźller, J.P. (2016)**. Iterative committee elections for collective decision- making in a ride-sharing application. In A. L. C. Bazzan, F KlĂźgl, S. Ossowski, G. Vizzari, eds., Proc. 9th International Workshop on Agents in Traffic and Transport (ATT 2016) at IJCAI 2016, 1-8\. New York, USA, CEUR. Electronic proceedings.
**Dennisen, S.; MĂźller, J.P. (2015)**. Agent-based voting architecture for traffic applications. In Proceedings of the 13th German Conference of Multiagent System Technologies (MATES 2015), volume 9433 of Lecture Notes in Artificial Intelligence, 200-217\. Springer-Verlag.


---
## A (Near?) Future Scenario: Ridesharing with PATs I

![image](slides/sociotechnicalsystems3/image_013.jpg)
<sup>[Source: NEXT Transportation Systems, https://www.youtube.com/watch?v=IDgh29SqZzE](http://www.youtube.com/watch?v=IDgh29SqZzE)<sup>

*   2030: _Personalized Autonomous Transportation_ Cells (PATs)
*   Modular 1-PATs can connect automatically to form N-PATs
*   Travellers provide preference information to PAT via App


---
## A (Near?) Future Scenario: Ridesharing with PATs II

*   CITY X pilots N-PAT based system replace CITY X pilots public transport in city centre:
    *   1-PATs are provided for hire
    *   N-PATs choose route based on passengers' preferences
*   CITY X traffic authority
    *   forbids 1-PATs to enter the town centre
    *   allows entry for 4-PATs or larger platoons


---
## Vehicle platoon formation for inner-city ride-sharing:Example scenario

![image](slides/sociotechnicalsystems3/image_012.png#centering)


---
## Vehicle platoons for inner-city ride-sharing: Example

![image](slides/sociotechnicalsystems3/image_032.png#centering)


---
## Vehicle platoons for inner-city ride-sharing: Example scenario
     
![image](slides/sociotechnicalsystems3/image_048.png#centering) 


---
## Vehicle platoon formation for inner-city ride-sharing: Example scenario

![image](slides/sociotechnicalsystems3/image_048.png#centering)


---
## Ridesharing Scenario: Scheduling city group tour <sup><sup><sup>(Dennisen and Müller, ATT 2016)

*   Travellers in 1-PATs join into N-PATs
*   Different preferences on POIs to visit
*   Committee elections within N-PATs: which POIs to visit?
*   Iterative algorithm, allows dissatisfied voters to leave

![image](slides/sociotechnicalsystems3/image_071.png#centering)


---
## Analysis of scenario - Research questions

*   _How can 1-PATs form into N-PAT in a way that assigns travellers with similar preferences to the same N-PAT?_
*   _How can travellers that are not satisfied with the route or that changed their opinion/preferences, be enabled to leave an N-PAT and join another?_
*   _Is there an optimal N-PAT size for certain sets of POIs ...?_
    _And with respect to which criteria?_
*   The scenario rests on the hypothesis that traffic efficiency, pedestrian comfort, and traffic safety can be increased by such technology. _Is this true?_


---
## Results: 
### Voting architecture and simulation testbed <sup><sup><sup><sup>(Dennisen & Müller, MATES 2015)

*   Requirements for traffic voting identified
*   Formal model and voting architecture
    <span>![image](slides/sociotechnicalsystems3/image_072.jpg)</span>
*   J-VOTING evaluation testbed based on <sup><sup>(Grimaldo et al, 2012)


---
## Results: 
### Iterative committee elections in a ride-sharing application <sup><sup>(Dennisen and MĂźller, ATT 2016)
*   Propose iterative winner determination protocol
    *   allows dissatisfied voters to leave
    *   guarantees that remaining voters are satisfied
*   Experimental evaluation
    *   How do different committee voting rules under an iterative protocol compare regarding the number of iterations required?
*   Voting rules considered
    *   Minisum Approval: Utilitarian approach - minimise sum of dissatisfaction values
    *   Minimax Approval: Egalitarian approach - minimise maximal dissatisfaction

$\rightarrow$ **_Results depend heavily on input parameters_** **dissatisfaction threshold** **_and_** **committee size**


---
## Voting in traffic applications: Many challenges remain!

*   Combinatorial voting: Voting on large numbers of alternatives
*   Voting in operation-level situations: Needs both, theoretical and practical work on time-constrained voting (e.g. anytime properties, upper bounds on runtimes)
    *   Scalability influenced by winner determination, communication protocols, application parameters, runtime platform, ...
*   **_How can preference information be elicited and securely managed?_ ...**


---
## Summary

*   Computational Social Choice Theory offers a rich set of tools for modelling/implementing collective decisions
*   There is no perfect mechanism: what works well, depends on the application characteristics
*   The way preferences (votes) are formulated has a great impact
*   There are similarities between game theory, computational social choice theory, decision theory, and auctions
*   All rely on the notions of agents, protocols, and mechanisms (environments)
*   All require mechanism design


---
## Conferences

*   International Joint Conference on Autonomous Agents and Multiagent Systems <sup><sup>(AAMAS, www.ifaamas.org)</sup></sup>
*   International Joint Conference on Artificial Intelligence <sup><sup>(www.ijcai.org)</sup></sup>
*   International Conference on Practical Applications of Agents and Multi-Agent Systems <sup><sup>(www.paams.net)</sup></sup>
*   IEEE/WIC/ACM International Conference on Intelligent Agent Technology (IAT)
*   EUMAS: European Conference on Multiagent Systems


---
## International Workshops I

*   ACAN: Agent-based Complex Automated Negotiations
*   ADAPT: Agent Design: Adapting from Practice to Theory
*   ADMI: Agent and Data Mining Interaction Workshop
*   ALA: Adaptive and Learning Agents
*   AMEC: Agent-Mediated Electronic Commerce
*   ArgMAS: Argumentation in Multi-Agent Systems
*   ARMS: Autonomous Robots and Multirobot Systems
*   ATT: Agents in Traffic and Transport
*   COIN: Coordination, Organizations, Institutions and Norms in Agent Systems


---
## International Workshops II

*   CoopMAS: Cooperative Games in Multiagent Systems
*   EMAS: International Workshop on Engineering Multiagent Systems
*   ITMAS: Infrastructures and Tools for Multiagent Systems
*   MABS: Multi-Agent-Based Simulation
*   MSDM: Multiagent Sequential Decision Making in Uncertain Domains
*   OPTMAS:Optimisation in Multi-Agent Systems
*   Trust in Agent Societies
*   And many others


---
## International Journals

*   Autonomous Agents and Multiagent Systems (JAAMAS), Springer <sup><sup>[http://www.springer.com/computer/ai/journal/10458](http://www.springer.com/computer/ai/journal/10458)
*   International Journal of Agent-oriented Software Engineeringv (IJAOSE), Inderscience, <sup><sup>[http://www.inderscience.com/browse/index.php?journalCODE=ijaose](http://www.inderscience.com/browse/index.php?journalCODE=ijaose)
*   Knowledge Engineering Review (KER), Cambridge Journals,<sup><sup>    [http://journals.cambridge.org/action/displayJournal?jid=KER](http://journals.cambridge.org/action/displayJournal?jid=KER)
*   Journal of Artificial Intelligence Research, Online Journal, <sup>[http://www.jair.org/](http://www.jair.org/)<sup> 
*   Artificial Intelligence (AI), Elsevier,<sup>[www.elsevier.com/wps/find/journaldescription.cws_home/505601/description#description](http://www.elsevier.com/wps/find/journaldescription.cws_home/505601/description#description)<sup>
