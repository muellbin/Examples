<!-- $theme: default -->
<!-- $size: 16:9 -->

<style>
svg.railroad-diagram path {
    stroke-width: 3;
    stroke: black;
    fill: rgba(0,0,0,0);
}
svg.railroad-diagram text {
    font: bold 14px monospace;
    text-anchor: middle;
}
svg.railroad-diagram text.diagram-text {
    font-size: 12px;
}
svg.railroad-diagram text.diagram-arrow {
    font-size: 16px;
}
svg.railroad-diagram text.label {
    text-anchor: start;
}
svg.railroad-diagram text.comment {
    font: italic 12px monospace;
}
svg.railroad-diagram g.non-terminal text {
    /*font-style: italic;*/
}
svg.railroad-diagram rect {
    stroke-width: 3;
    stroke: black;
    fill: hsl(120,100%,90%);
}
svg.railroad-diagram path.diagram-text {
    stroke-width: 3;
    stroke: black;
    fill: white;
    cursor: help;
}
svg.railroad-diagram g.diagram-text:hover path.diagram-text {
    fill: #eee;
}
</style>

# LightJason
Agent-Orientated programming with AgentSpeak(L++)
 
---

<!-- page_number: true -->

## Lecture Recap

* [AgentSpeak(L)](https://en.wikipedia.org/wiki/AgentSpeak) introduced in 1996 from A. Rao
	* uses the [belief-desire-intention (BDI)](https://en.wikipedia.org/wiki/Belief%E2%80%93desire%E2%80%93intention_model) architecture 
	* execution mechanisum is [Procedural reasoning system (PRS)](https://en.wikipedia.org/wiki/Procedural_reasoning_system)
	* data is described in _symbols and facts_ with is used in a [logical programming language](https://en.wikipedia.org/wiki/Logic_programming)
* We extend these basic models to _AgentSpeak(L++)_
	* clean and strict syntax with state-of-the-art features
	* append the PRS with concurrency mechanism
	* boost-up the execution mechanism up to approximatly 2.6 Mio agents on a desktop computer

---





## AOP - Agent Cycle

Agents are executed by the runtime of the simulation and do the following two steps:

1. can update all beliefs
	perceives the evnironment with the sensors
2. executes all possible plans
	do something with the sensor data
    and follows-up other plans from the preceding cycle

> __But each step is done in a parallel execution, so all data of the sensors are collection in parallel and all possible plans are run in parallel and all agents work in parallel__

---


## AOP - Literal, Beliefs, Facts

The agent needs to store data, the _literals_ - also named _facts_ -

http://lightjason.github.io/AgentSpeak/rrd-output/html/org/lightjason/agentspeak/grammar/Agent.g4/

#### Literal

<svg class="railroad-diagram" width="872" height="109" viewBox="0 0 872 109" id="svg_f0d674f1e0ed4292267f149c5983db02"><g transform="translate(.5 .5)"><path d="M 20 38 v 20 m 10 -20 v 20 m -10 -10 h 20.5"></path><path d="M40 48h10"></path><g><path d="M50 48h0"></path><path d="M822 48h0"></path><g><path d="M50 48h0"></path><path d="M262 48h0"></path><path d="M50 48a10 10 0 0 0 10 -10v0a10 10 0 0 1 10 -10"></path><g><path d="M70 28h172"></path></g><path d="M242 28a10 10 0 0 1 10 10v0a10 10 0 0 0 10 10"></path><path d="M50 48h20"></path><g><path d="M70 48h0"></path><path d="M242 48h0"></path><path d="M70 48h20"></path><g class="non-terminal"><path d="M90 48h48"></path><path d="M174 48h48"></path><rect x="138" y="37" width="36" height="22"></rect><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#fa868488740aa25870ced6b9169951fb"><text x="156" y="52">AT</text></a></g><path d="M222 48h20"></path><path d="M70 48a10 10 0 0 1 10 10v10a10 10 0 0 0 10 10"></path><g class="non-terminal"><path d="M90 78h0"></path><path d="M222 78h0"></path><rect x="90" y="67" width="132" height="22"></rect><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#207b9b679fb614699f3d949f6fc63218"><text x="156" y="82">STRONGNEGATION</text></a></g><path d="M222 78a10 10 0 0 0 10 -10v-10a10 10 0 0 1 10 -10"></path></g><path d="M242 48h20"></path></g><path d="M262 48h10"></path><g class="non-terminal"><path d="M272 48h0"></path><path d="M324 48h0"></path><rect x="272" y="37" width="52" height="22"></rect><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#3e10f8c809242d3a0f94c18e7addb866"><text x="298" y="52">atom</text></a></g><path d="M324 48h10"></path><g><path d="M334 48h0"></path><path d="M822 48h0"></path><path d="M334 48a10 10 0 0 0 10 -10v-8a10 10 0 0 1 10 -10"></path><g><path d="M354 20h448"></path></g><path d="M802 20a10 10 0 0 1 10 10v8a10 10 0 0 0 10 10"></path><path d="M334 48h20"></path><g><path d="M354 48h0"></path><path d="M802 48h0"></path><g class="non-terminal"><path d="M354 48h0"></path><path d="M502 48h0"></path><rect x="354" y="37" width="148" height="22"></rect><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#5ffa5d1c78ad09c7bf5b4d0b0764641f"><text x="428" y="52">LEFTROUNDBRACKET</text></a></g><path d="M502 48h10"></path><g><path d="M512 48h0"></path><path d="M636 48h0"></path><path d="M512 48a10 10 0 0 0 10 -10v0a10 10 0 0 1 10 -10"></path><g><path d="M532 28h84"></path></g><path d="M616 28a10 10 0 0 1 10 10v0a10 10 0 0 0 10 10"></path><path d="M512 48h20"></path><g class="non-terminal"><path d="M532 48h0"></path><path d="M616 48h0"></path><rect x="532" y="37" width="84" height="22"></rect><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#45e9c6711e26d65a3189b502fd08a63"><text x="574" y="52">termlist</text></a></g><path d="M616 48h20"></path></g><path d="M636 48h10"></path><g class="non-terminal"><path d="M646 48h0"></path><path d="M802 48h0"></path><rect x="646" y="37" width="156" height="22"></rect><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#3a52152b9f1e9dd45998ce24723d98ed"><text x="724" y="52">RIGHTROUNDBRACKET</text></a></g></g><path d="M802 48h20"></path></g></g><path d="M822 48h10"></path><path d="M 832 48 h 20 m -10 -10 v 20 m 10 -20 v 20"></path></g></svg>


#### Atom

<svg class="railroad-diagram" width="490" height="229" viewBox="0 0 490 229" id="svg_3e10f8c809242d3a0f94c18e7addb866"><g transform="translate(.5 .5)"><path d="M 20 30 v 20 m 10 -20 v 20 m -10 -10 h 20.5"></path><path d="M40 40h10"></path><g><path d="M50 40h0"></path><path d="M440 40h0"></path><g class="non-terminal"><path d="M50 40h0"></path><path d="M190 40h0"></path><rect x="50" y="29" width="140" height="22"></rect><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#28e746830337961c5de40b87c99980a6"><text x="120" y="44">LOWERCASELETTER</text></a></g><path d="M190 40h10"></path><g><path d="M200 40h0"></path><path d="M440 40h0"></path><path d="M200 40a10 10 0 0 0 10 -10v0a10 10 0 0 1 10 -10"></path><g><path d="M220 20h200"></path></g><path d="M420 20a10 10 0 0 1 10 10v0a10 10 0 0 0 10 10"></path><path d="M200 40h20"></path><g><path d="M220 40h0"></path><path d="M420 40h0"></path><path d="M220 40h10"></path><g><path d="M230 40h0"></path><path d="M410 40h0"></path><path d="M230 40h20"></path><g class="non-terminal"><path d="M250 40h0"></path><path d="M390 40h0"></path><rect x="250" y="29" width="140" height="22"></rect><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#28e746830337961c5de40b87c99980a6"><text x="320" y="44">LOWERCASELETTER</text></a></g><path d="M390 40h20"></path><path d="M230 40a10 10 0 0 1 10 10v10a10 10 0 0 0 10 10"></path><g class="non-terminal"><path d="M250 70h0"></path><path d="M390 70h0"></path><rect x="250" y="59" width="140" height="22"></rect><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#c857e06a9c6dcbcfd625e4859380c98e"><text x="320" y="74">UPPERCASELETTER</text></a></g><path d="M390 70a10 10 0 0 0 10 -10v-10a10 10 0 0 1 10 -10"></path><path d="M230 40a10 10 0 0 1 10 10v40a10 10 0 0 0 10 10"></path><g class="non-terminal"><path d="M250 100h20"></path><path d="M370 100h20"></path><rect x="270" y="89" width="100" height="22"></rect><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#a058c5dc734e54ff3d93b96acac339f4"><text x="320" y="104">UNDERSCORE</text></a></g><path d="M390 100a10 10 0 0 0 10 -10v-40a10 10 0 0 1 10 -10"></path><path d="M230 40a10 10 0 0 1 10 10v70a10 10 0 0 0 10 10"></path><g class="non-terminal"><path d="M250 130h40"></path><path d="M350 130h40"></path><rect x="290" y="119" width="60" height="22"></rect><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#999bbbf1d86bc7611397c77222e076db"><text x="320" y="134">DIGIT</text></a></g><path d="M390 130a10 10 0 0 0 10 -10v-70a10 10 0 0 1 10 -10"></path><path d="M230 40a10 10 0 0 1 10 10v100a10 10 0 0 0 10 10"></path><g class="non-terminal"><path d="M250 160h40"></path><path d="M350 160h40"></path><rect x="290" y="149" width="60" height="22"></rect><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#646da671ca01bb5d84dbb5fb2238dc8e"><text x="320" y="164">SLASH</text></a></g><path d="M390 160a10 10 0 0 0 10 -10v-100a10 10 0 0 1 10 -10"></path><path d="M230 40a10 10 0 0 1 10 10v130a10 10 0 0 0 10 10"></path><g class="non-terminal"><path d="M250 190h40"></path><path d="M350 190h40"></path><rect x="290" y="179" width="60" height="22"></rect><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#ffc0d9b54a1fe677c4c9e6b050e67c81"><text x="320" y="194">MINUS</text></a></g><path d="M390 190a10 10 0 0 0 10 -10v-130a10 10 0 0 1 10 -10"></path></g><path d="M410 40h10"></path><path d="M230 40a10 10 0 0 0 -10 10v149a10 10 0 0 0 10 10"></path><g><path d="M230 209h180"></path></g><path d="M410 209a10 10 0 0 0 10 -10v-149a10 10 0 0 0 -10 -10"></path></g><path d="M420 40h20"></path></g></g><path d="M440 40h10"></path><path d="M 450 40 h 20 m -10 -10 v 20 m 10 -20 v 20"></path></g></svg>


---

## AOP - Actions

https://lightjason.github.io/knowledgebase/actions/

---


## AOP - Plans

#### Plan

<svg class="railroad-diagram" width="726" height="80" viewBox="0 0 726 80" id="svg_5fc25157650d0cb24f02216d904584df"><g transform="translate(.5 .5)"><path d="M 20 30 v 20 m 10 -20 v 20 m -10 -10 h 20.5"></path><path d="M40 40h10"></path><g><path d="M50 40h0"></path><path d="M676 40h0"></path><g><path d="M50 40h0"></path><path d="M198 40h0"></path><path d="M50 40a10 10 0 0 0 10 -10v0a10 10 0 0 1 10 -10"></path><g><path d="M70 20h108"></path></g><path d="M178 20a10 10 0 0 1 10 10v0a10 10 0 0 0 10 10"></path><path d="M50 40h20"></path><g class="non-terminal"><path d="M70 40h0"></path><path d="M178 40h0"></path><rect x="70" y="29" width="108" height="22"></rect><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#4ab6864fc58ecd8b598ee10dfe2ac311"><text x="124" y="44">annotations</text></a></g><path d="M178 40h20"></path></g><path d="M198 40h10"></path><g class="non-terminal"><path d="M208 40h0"></path><path d="M324 40h0"></path><rect x="208" y="29" width="116" height="22"></rect><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#4f0fa1b5875427a602b3f913163be2ca"><text x="266" y="44">plan_trigger</text></a></g><path d="M324 40h10"></path><path d="M334 40h10"></path><g class="non-terminal"><path d="M344 40h0"></path><path d="M420 40h0"></path><rect x="344" y="29" width="76" height="22"></rect><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#f0d674f1e0ed4292267f149c5983db02"><text x="382" y="44">literal</text></a></g><path d="M420 40h10"></path><g><path d="M430 40h0"></path><path d="M622 40h0"></path><path d="M430 40a10 10 0 0 0 10 -10v0a10 10 0 0 1 10 -10"></path><g><path d="M450 20h152"></path></g><path d="M602 20a10 10 0 0 1 10 10v0a10 10 0 0 0 10 10"></path><path d="M430 40h20"></path><g><path d="M450 40h0"></path><path d="M602 40h0"></path><path d="M450 40h10"></path><g class="non-terminal"><path d="M460 40h0"></path><path d="M592 40h0"></path><rect x="460" y="29" width="132" height="22"></rect><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#d60b4a42e52668da3017e5717ef3f60"><text x="526" y="44">plandefinition</text></a></g><path d="M592 40h10"></path><path d="M460 40a10 10 0 0 0 -10 10v0a10 10 0 0 0 10 10"></path><g><path d="M460 60h132"></path></g><path d="M592 60a10 10 0 0 0 10 -10v0a10 10 0 0 0 -10 -10"></path></g><path d="M602 40h20"></path></g><path d="M622 40h10"></path><g class="non-terminal"><path d="M632 40h0"></path><path d="M676 40h0"></path><rect x="632" y="29" width="44" height="22"></rect><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#40679521b5da0954b705341a2859f782"><text x="654" y="44">DOT</text></a></g></g><path d="M676 40h10"></path><path d="M 686 40 h 20 m -10 -10 v 20 m 10 -20 v 20"></path></g></svg>


#### Plan-Trigger
<svg class="railroad-diagram" width="292" height="92" viewBox="0 0 292 92" id="svg_4f0fa1b5875427a602b3f913163be2ca"><g transform="translate(.5 .5)"><path d="M 20 21 v 20 m 10 -20 v 20 m -10 -10 h 20.5"></path><g><path d="M40 31h0"></path><path d="M252 31h0"></path><path d="M40 31h20"></path><g class="non-terminal"><path d="M60 31h0"></path><path d="M232 31h0"></path><rect x="60" y="20" width="172" height="22"></rect><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#858bcfe2e941e450adf21315fc0aa172"><text x="146" y="35">plan_belief_trigger</text></a></g><path d="M232 31h20"></path><path d="M40 31a10 10 0 0 1 10 10v10a10 10 0 0 0 10 10"></path><g class="non-terminal"><path d="M60 61h8"></path><path d="M224 61h8"></path><rect x="68" y="50" width="156" height="22"></rect><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#37e402d5ccf7ec7d8ed9ebd1d8f2fe97"><text x="146" y="65">plan_goal_trigger</text></a></g><path d="M232 61a10 10 0 0 0 10 -10v-10a10 10 0 0 1 10 -10"></path></g><path d="M 252 31 h 20 m -10 -10 v 20 m 10 -20 v 20"></path></g></svg>

---

### AOP- Goal-Plans

#### Goal-Trigger

<svg class="railroad-diagram" width="350" height="92" viewBox="0 0 350 92" id="svg_37e402d5ccf7ec7d8ed9ebd1d8f2fe97"><g transform="translate(.5 .5)"><path d="M 20 21 v 20 m 10 -20 v 20 m -10 -10 h 20.5"></path><path d="M40 31h10"></path><g><path d="M50 31h0"></path><path d="M300 31h0"></path><g><path d="M50 31h0"></path><path d="M150 31h0"></path><path d="M50 31h20"></path><g class="non-terminal"><path d="M70 31h4"></path><path d="M126 31h4"></path><rect x="74" y="20" width="52" height="22"></rect><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#883acd43c77567e1c3baced84ccf6ed7"><text x="100" y="35">PLUS</text></a></g><path d="M130 31h20"></path><path d="M50 31a10 10 0 0 1 10 10v10a10 10 0 0 0 10 10"></path><g class="non-terminal"><path d="M70 61h0"></path><path d="M130 61h0"></path><rect x="70" y="50" width="60" height="22"></rect><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#ffc0d9b54a1fe677c4c9e6b050e67c81"><text x="100" y="65">MINUS</text></a></g><path d="M130 61a10 10 0 0 0 10 -10v-10a10 10 0 0 1 10 -10"></path></g><path d="M150 31h10"></path><g class="non-terminal"><path d="M160 31h0"></path><path d="M300 31h0"></path><rect x="160" y="20" width="140" height="22"></rect><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#a811f517fa7f9ba04cf05d3a6c777799"><text x="230" y="35">EXCLAMATIONMARK</text></a></g></g><path d="M300 31h10"></path><path d="M 310 31 h 20 m -10 -10 v 20 m 10 -20 v 20"></path></g></svg>

#### Add Goal

```
!main.

+!main <-
    generic/print("Hello World!");
    !mynextgoal
.

+!mynextgoal <-
    generic/print("Hello World! (again)", Cycle);
    !mynextgoal
.
```

#### Delete Goal

---

### AOP - Belief-Plans

#### Belief-Trigger

<svg class="railroad-diagram" width="180" height="92" viewBox="0 0 180 92" id="svg_858bcfe2e941e450adf21315fc0aa172"><g transform="translate(.5 .5)"><path d="M 20 21 v 20 m 10 -20 v 20 m -10 -10 h 20.5"></path><g><path d="M40 31h0"></path><path d="M140 31h0"></path><path d="M40 31h20"></path><g class="non-terminal"><path d="M60 31h4"></path><path d="M116 31h4"></path><rect x="64" y="20" width="52" height="22"></rect><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#883acd43c77567e1c3baced84ccf6ed7"><text x="90" y="35">PLUS</text></a></g><path d="M120 31h20"></path><path d="M40 31a10 10 0 0 1 10 10v10a10 10 0 0 0 10 10"></path><g class="non-terminal"><path d="M60 61h0"></path><path d="M120 61h0"></path><rect x="60" y="50" width="60" height="22"></rect><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#ffc0d9b54a1fe677c4c9e6b050e67c81"><text x="90" y="65">MINUS</text></a></g><path d="M120 61a10 10 0 0 0 10 -10v-10a10 10 0 0 1 10 -10"></path></g><path d="M 140 31 h 20 m -10 -10 v 20 m 10 -20 v 20"></path></g></svg>

---




## Execution - Finite-State-Machine

---

## Execution - Unification

<svg class="railroad-diagram" width="1052" height="101" viewBox="0 0 1052 101" id="svg_e732ce4bb8479dc479e294d62beaf1cf"><g transform="translate(.5 .5)"><path d="M 20 30 v 20 m 10 -20 v 20 m -10 -10 h 20.5"></path><path d="M40 40h10"></path><g><path d="M50 40h0"></path><path d="M1002 40h0"></path><g><path d="M50 40h0"></path><path d="M126 40h0"></path><path d="M50 40a10 10 0 0 0 10 -10v0a10 10 0 0 1 10 -10"></path><g><path d="M70 20h36"></path></g><path d="M106 20a10 10 0 0 1 10 10v0a10 10 0 0 0 10 10"></path><path d="M50 40h20"></path><g class="non-terminal"><path d="M70 40h0"></path><path d="M106 40h0"></path><rect x="70" y="29" width="36" height="22"></rect><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#fa868488740aa25870ced6b9169951fb"><text x="88" y="44">AT</text></a></g><path d="M106 40h20"></path></g><path d="M126 40h10"></path><g class="non-terminal"><path d="M136 40h0"></path><path d="M236 40h0"></path><rect x="136" y="29" width="100" height="22"></rect><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#f2160f407f56e0f4d495cecd44055e2d"><text x="186" y="44">RIGHTSHIFT</text></a></g><path d="M236 40h10"></path><g><path d="M246 40h0"></path><path d="M1002 40h0"></path><path d="M246 40h20"></path><g class="non-terminal"><path d="M266 40h320"></path><path d="M662 40h320"></path><rect x="586" y="29" width="76" height="22"></rect><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#f0d674f1e0ed4292267f149c5983db02"><text x="624" y="44">literal</text></a></g><path d="M982 40h20"></path><path d="M246 40a10 10 0 0 1 10 10v10a10 10 0 0 0 10 10"></path><g><path d="M266 70h0"></path><path d="M982 70h0"></path><g class="non-terminal"><path d="M266 70h0"></path><path d="M414 70h0"></path><rect x="266" y="59" width="148" height="22"></rect><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#5ffa5d1c78ad09c7bf5b4d0b0764641f"><text x="340" y="74">LEFTROUNDBRACKET</text></a></g><path d="M414 70h10"></path><path d="M424 70h10"></path><g class="non-terminal"><path d="M434 70h0"></path><path d="M510 70h0"></path><rect x="434" y="59" width="76" height="22"></rect><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#f0d674f1e0ed4292267f149c5983db02"><text x="472" y="74">literal</text></a></g><path d="M510 70h10"></path><path d="M520 70h10"></path><g class="non-terminal"><path d="M530 70h0"></path><path d="M590 70h0"></path><rect x="530" y="59" width="60" height="22"></rect><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#4d9b3e9fc12849d060371eb65154c751"><text x="560" y="74">COMMA</text></a></g><path d="M590 70h10"></path><path d="M600 70h10"></path><g class="non-terminal"><path d="M610 70h0"></path><path d="M806 70h0"></path><rect x="610" y="59" width="196" height="22"></rect><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#89368367b9f48fd82a781f5a4e1ad8b6"><text x="708" y="74">unification_constraint</text></a></g><path d="M806 70h10"></path><path d="M816 70h10"></path><g class="non-terminal"><path d="M826 70h0"></path><path d="M982 70h0"></path><rect x="826" y="59" width="156" height="22"></rect><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#3a52152b9f1e9dd45998ce24723d98ed"><text x="904" y="74">RIGHTROUNDBRACKET</text></a></g></g><path d="M982 70a10 10 0 0 0 10 -10v-10a10 10 0 0 1 10 -10"></path></g></g><path d="M1002 40h10"></path><path d="M 1012 40 h 20 m -10 -10 v 20 m 10 -20 v 20"></path></g></svg>

---







## Theory - Close- and Open-World-Assumption

---


## Theory - Architecture








---

## Exercise - Agent Example

---

## Exercise - Scenario

---

## Exercise - Concepts and Hints

---

## Exercise - Challange
