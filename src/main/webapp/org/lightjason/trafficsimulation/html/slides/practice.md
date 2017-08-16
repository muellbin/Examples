<!-- $theme: default -->
<!-- $size: 16:9 -->

<style>
svg.railroad-diagram path {
    stroke-width: 2;
    stroke: grey;
    fill: rgba(0,0,0,0);
}
svg.railroad-diagram text {
    font: bold 14px monospace;
    text-anchor: middle;
}
svg.railroad-diagram rect {
    stroke-width: 3;
    stroke: black;
}

svg.railroad-diagram .non-terminal {
    fill: #14811a;
}
</style>

# LightJason
Agent-Orientated programming with AgentSpeak(L++)


---
<!-- page_number: true -->
## Lecture Recap I

* History: [AgentSpeak(L)](https://en.wikipedia.org/wiki/AgentSpeak) \[Rao, 1996; Bordini et al. 2007\]
	* [Belief-desire-intention (BDI)](https://en.wikipedia.org/wiki/Belief%E2%80%93desire%E2%80%93intention_model) architecture 
	* [Procedural reasoning system (PRS)](https://en.wikipedia.org/wiki/Procedural_reasoning_system)  as execution mechanism 
	* [Logical programming language](https://en.wikipedia.org/wiki/Logic_programming) to represent data with _symbols_ and _facts_
	* Java reference implementation [Jason](http://jason.sourceforge.net/) by Rafael H. Bordini & Jomi F. Hübner

---
## Lecture Recap II

* Based on the work of Rao, Bordini and Hübner, we designed the agent language AgentSpeak(L++) in [[Aschermann, Kraus, 2006]](https://lightjason.github.io/publication/2016-eumas.pdf) and [reimplemented it from scratch](https://github.com/LightJason/AgentSpeak), featuring
	* clean and strict syntax with state-of-the-art technologies
	* [well](https://lightjason.github.io) [documented](http://lightjason.github.io/AgentSpeak/rrd-output/html/org/lightjason/agentspeak/grammar/Agent.g4/) [software](http://lightjason.github.io/AgentSpeak/sources/) (not just "documentation by research papers")
	* extended PRS with a mechanism optimised for concurrency
	* [REST-API](https://en.wikipedia.org/wiki/Representational_state_transfer) for flexible, system independed control of [multi-agent system (MAS)](https://en.wikipedia.org/wiki/Multi-agent_system)
	* scalable, concurrent execution of approximately 2.6 Mio agents on a desktop computer
	* additional support for cloud computing, for example [Hadoop](https://en.wikipedia.org/wiki/Apache_Hadoop)


---
## AOP - Symbolic Representation

Data structures are defined as _symbolic represantation_:

##### Do not define like in an imperative programming language with a type and a variable name like 
```java 
int value = 5;
```   

##### Describing a _green traffic light_ with 
```prolog
trafficlight(green).
```
    
##### Describing _parking not allowed_ with 
```prolog
~allowed(parking).
```

---
### Atom

Atoms are **unsplitable** elements which define the basic elements in the programming language

<svg height=300px class="railroad-diagram centering" viewBox="0 0 490 229" id="svg_3e10f8c809242d3a0f94c18e7addb866"><path d="M20 30v20m10-20v20M20 40h20.5m-.5 0h10m390 0" transform="translate(.5 .5)"/><g class="non-terminal" transform="translate(.5 .5)"><path d="M50 29h140v22H50z"/><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#28e746830337961c5de40b87c99980a6"><text x="120" y="44">LOWERCASELETTER</text></a></g><path d="M190 40h10m0 0a10 10 0 0 0 10-10 10 10 0 0 1 10-10m0 0h200m0 0a10 10 0 0 1 10 10 10 10 0 0 0 10 10m-240 0h20m0 0h10m0 0h20" transform="translate(.5 .5)"/><g class="non-terminal" transform="translate(.5 .5)"><path d="M250 29h140v22H250z"/><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#28e746830337961c5de40b87c99980a6"><text x="320" y="44">LOWERCASELETTER</text></a></g><path d="M390 40h20m-180 0a10 10 0 0 1 10 10v10a10 10 0 0 0 10 10" transform="translate(.5 .5)"/><g class="non-terminal" transform="translate(.5 .5)"><path d="M250 59h140v22H250z"/><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#c857e06a9c6dcbcfd625e4859380c98e"><text x="320" y="74">UPPERCASELETTER</text></a></g><path d="M390 70a10 10 0 0 0 10-10V50a10 10 0 0 1 10-10m-180 0a10 10 0 0 1 10 10v40a10 10 0 0 0 10 10" transform="translate(.5 .5)"/><g class="non-terminal" transform="translate(.5 .5)"><path d="M250 100h20m100 0h20M270 89h100v22H270z"/><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#a058c5dc734e54ff3d93b96acac339f4"><text x="320" y="104">UNDERSCORE</text></a></g><path d="M390 100a10 10 0 0 0 10-10V50a10 10 0 0 1 10-10m-180 0a10 10 0 0 1 10 10v70a10 10 0 0 0 10 10" transform="translate(.5 .5)"/><g class="non-terminal" transform="translate(.5 .5)"><path d="M250 130h40m60 0h40m-100-11h60v22h-60z"/><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#999bbbf1d86bc7611397c77222e076db"><text x="320" y="134">DIGIT</text></a></g><path d="M390 130a10 10 0 0 0 10-10V50a10 10 0 0 1 10-10m-180 0a10 10 0 0 1 10 10v100a10 10 0 0 0 10 10" transform="translate(.5 .5)"/><g class="non-terminal" transform="translate(.5 .5)"><path d="M250 160h40m60 0h40m-100-11h60v22h-60z"/><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#646da671ca01bb5d84dbb5fb2238dc8e"><text x="320" y="164">SLASH</text></a></g><path d="M390 160a10 10 0 0 0 10-10V50a10 10 0 0 1 10-10m-180 0a10 10 0 0 1 10 10v130a10 10 0 0 0 10 10" transform="translate(.5 .5)"/><g class="non-terminal" transform="translate(.5 .5)"><path d="M250 190h40m60 0h40m-100-11h60v22h-60z"/><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#ffc0d9b54a1fe677c4c9e6b050e67c81"><text x="320" y="194">MINUS</text></a></g><path d="M390 190a10 10 0 0 0 10-10V50a10 10 0 0 1 10-10m0 0h10m-190 0a10 10 0 0 0-10 10v149a10 10 0 0 0 10 10m0 0h180m0 0a10 10 0 0 0 10-10V50a10 10 0 0 0-10-10m10 0h20m0 0h10m0 0h20m-10-10v20m10-20v20" transform="translate(.5 .5)"/></svg>




---
### Term

A term is **any value- and data-type** within the language

<svg width=600px class="railroad-diagram centering" viewBox="0 0 580 302" id="svg_b4dad0fe5fbef2c0e24d9db1cc69e5a2"><path d="M20 21v20m10-20v20M20 31h20.5m-.5 0h20" transform="translate(.5 .5)"/><g class="non-terminal" transform="translate(.5 .5)"><path d="M60 31h196m68 0h196M256 20h68v22h-68z"/><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#b45cffe084dd3d20d928bee85e7b0f21"><text x="290" y="35">string</text></a></g><path d="M520 31h20M40 31a10 10 0 0 1 10 10v10a10 10 0 0 0 10 10" transform="translate(.5 .5)"/><g class="non-terminal" transform="translate(.5 .5)"><path d="M60 61h196m68 0h196M256 50h68v22h-68z"/><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#b1bc248a7ff2b2e95569f56de68615df"><text x="290" y="65">number</text></a></g><path d="M520 61a10 10 0 0 0 10-10V41a10 10 0 0 1 10-10M40 31a10 10 0 0 1 10 10v40a10 10 0 0 0 10 10" transform="translate(.5 .5)"/><g class="non-terminal" transform="translate(.5 .5)"><path d="M60 91h172m116 0h172M232 80h116v22H232z"/><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#af6a9878b68b9081f2f32558fc1c5f42"><text x="290" y="95">logicalvalue</text></a></g><path d="M520 91a10 10 0 0 0 10-10V41a10 10 0 0 1 10-10M40 31a10 10 0 0 1 10 10v70a10 10 0 0 0 10 10" transform="translate(.5 .5)"/><g class="non-terminal" transform="translate(.5 .5)"><path d="M60 121h192m76 0h192m-268-11h76v22h-76z"/><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#f0d674f1e0ed4292267f149c5983db02"><text x="290" y="125">literal</text></a></g><path d="M520 121a10 10 0 0 0 10-10V41a10 10 0 0 1 10-10M40 31a10 10 0 0 1 10 10v100a10 10 0 0 0 10 10" transform="translate(.5 .5)"/><g class="non-terminal" transform="translate(.5 .5)"><path d="M60 151h188m84 0h188m-272-11h84v22h-84z"/><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#e04aa5104d082e4a51d241391941ba26"><text x="290" y="155">variable</text></a></g><path d="M520 151a10 10 0 0 0 10-10V41a10 10 0 0 1 10-10M40 31a10 10 0 0 1 10 10v130a10 10 0 0 0 10 10" transform="translate(.5 .5)"/><g class="non-terminal" transform="translate(.5 .5)"><path d="M60 181h172m116 0h172m-288-11h116v22H232z"/><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#aa70971153fc735cddfeb6720c3303c9"><text x="290" y="185">variablelist</text></a></g><path d="M520 181a10 10 0 0 0 10-10V41a10 10 0 0 1 10-10M40 31a10 10 0 0 1 10 10v160a10 10 0 0 0 10 10m460 0" transform="translate(.5 .5)"/><g class="non-terminal" transform="translate(.5 .5)"><path d="M60 200h164v22H60z"/><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#b11464d4ff702d93244a8e2a7f6ba3bf"><text x="142" y="215">LEFTANGULARBRACKET</text></a></g><path d="M224 211h10m0 0h10" transform="translate(.5 .5)"/><g class="non-terminal" transform="translate(.5 .5)"><path d="M244 200h84v22h-84z"/><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#45e9c6711e26d65a3189b502fd08a63"><text x="286" y="215">termlist</text></a></g><path d="M328 211h10m0 0h10" transform="translate(.5 .5)"/><g class="non-terminal" transform="translate(.5 .5)"><path d="M348 200h172v22H348z"/><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#a025c9443af11da0298acc93764673e7"><text x="434" y="215">RIGHTANGULARBRACKET</text></a></g><path d="M520 211a10 10 0 0 0 10-10V41a10 10 0 0 1 10-10M40 31a10 10 0 0 1 10 10v190a10 10 0 0 0 10 10" transform="translate(.5 .5)"/><g class="non-terminal" transform="translate(.5 .5)"><path d="M60 241h180m100 0h180m-280-11h100v22H240z"/><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#63973cd3ad7ccf2c8d5dce94b215f683"><text x="290" y="245">expression</text></a></g><path d="M520 241a10 10 0 0 0 10-10V41a10 10 0 0 1 10-10M40 31a10 10 0 0 1 10 10v220a10 10 0 0 0 10 10" transform="translate(.5 .5)"/><g class="non-terminal" transform="translate(.5 .5)"><path d="M60 271h152m156 0h152m-308-11h156v22H212z"/><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#a3e531edbe77ec3a390a2671be0905b8"><text x="290" y="275">ternary_operation</text></a></g><path d="M520 271a10 10 0 0 0 10-10V41a10 10 0 0 1 10-10m0 0h20m-10-10v20m10-20v20" transform="translate(.5 .5)"/></svg>


---
### Literal

A literal is the conclusion of terms and atoms, it starts with a **lower-case letter**

<svg class="railroad-diagram centering" viewBox="0 0 872 109" id="svg_f0d674f1e0ed4292267f149c5983db02"><path d="M20 38v20m10-20v20M20 48h20.5m-.5 0h10m0 0a10 10 0 0 0 10-10 10 10 0 0 1 10-10m0 0h172m0 0a10 10 0 0 1 10 10 10 10 0 0 0 10 10M50 48h20m0 0h20" transform="translate(.5 .5)"/><g class="non-terminal" transform="translate(.5 .5)"><path d="M90 48h48m36 0h48m-84-11h36v22h-36z"/><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#fa868488740aa25870ced6b9169951fb"><text x="156" y="52">AT</text></a></g><path d="M222 48h20M70 48a10 10 0 0 1 10 10v10a10 10 0 0 0 10 10" transform="translate(.5 .5)"/><g class="non-terminal" transform="translate(.5 .5)"><path d="M90 67h132v22H90z"/><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#207b9b679fb614699f3d949f6fc63218"><text x="156" y="82">STRONGNEGATION</text></a></g><path d="M222 78a10 10 0 0 0 10-10V58a10 10 0 0 1 10-10m0 0h20m0 0h10" transform="translate(.5 .5)"/><g class="non-terminal" transform="translate(.5 .5)"><path d="M272 37h52v22h-52z"/><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#3e10f8c809242d3a0f94c18e7addb866"><text x="298" y="52">atom</text></a></g><path d="M324 48h10m0 0a10 10 0 0 0 10-10v-8a10 10 0 0 1 10-10m0 0h448m0 0a10 10 0 0 1 10 10v8a10 10 0 0 0 10 10m-488 0h20m448 0" transform="translate(.5 .5)"/><g class="non-terminal" transform="translate(.5 .5)"><path d="M354 37h148v22H354z"/><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#5ffa5d1c78ad09c7bf5b4d0b0764641f"><text x="428" y="52">LEFTROUNDBRACKET</text></a></g><path d="M502 48h10m0 0a10 10 0 0 0 10-10 10 10 0 0 1 10-10m0 0h84m0 0a10 10 0 0 1 10 10 10 10 0 0 0 10 10m-124 0h20" transform="translate(.5 .5)"/><g class="non-terminal" transform="translate(.5 .5)"><path d="M532 37h84v22h-84z"/><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#45e9c6711e26d65a3189b502fd08a63"><text x="574" y="52">termlist</text></a></g><path d="M616 48h20m0 0h10" transform="translate(.5 .5)"/><g class="non-terminal" transform="translate(.5 .5)"><path d="M646 37h156v22H646z"/><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#3a52152b9f1e9dd45998ce24723d98ed"><text x="724" y="52">RIGHTROUNDBRACKET</text></a></g><path d="M802 48h20m0 0h10m0 0h20m-10-10v20m10-20v20" transform="translate(.5 .5)"/></svg>


---
### Belief - Fact

Any literal can be a belief or fact:

* Any information is stored as a fact or belief, but we can distinguish it on our context, both are literals
* A belief is generated by the agent or by the sensors of the agent, which perceive the environment, but sensor data can be wrong iif the sensor is defect, so a .important[belief can be true, but need not to be true]
* Agents can generate beliefs, so the agent can _conclude_ anything by combining beliefs to a new one


---
### Variables

Variables are special terms to store data during runtime, there are two kinds of variables

<svg height=300px class="railroad-diagram centering" viewBox="0 0 520 199" id="svg_70d97cd67aba0712a4ac127a7307ad12"><path d="M20 30v20m10-20v20M20 40h20.5m-.5 0h10m0 0h20" transform="translate(.5 .5)"/><g class="non-terminal" transform="translate(.5 .5)"><path d="M70 29h140v22H70z"/><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#c857e06a9c6dcbcfd625e4859380c98e"><text x="140" y="44">UPPERCASELETTER</text></a></g><path d="M210 40h20M50 40a10 10 0 0 1 10 10v10a10 10 0 0 0 10 10" transform="translate(.5 .5)"/><g class="non-terminal" transform="translate(.5 .5)"><path d="M70 70h20m100 0h20M90 59h100v22H90z"/><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#a058c5dc734e54ff3d93b96acac339f4"><text x="140" y="74">UNDERSCORE</text></a></g><path d="M210 70a10 10 0 0 0 10-10V50a10 10 0 0 1 10-10m0 0a10 10 0 0 0 10-10 10 10 0 0 1 10-10m0 0h200m0 0a10 10 0 0 1 10 10 10 10 0 0 0 10 10m-240 0h20m0 0h10m0 0h20" transform="translate(.5 .5)"/><g class="non-terminal" transform="translate(.5 .5)"><path d="M280 29h140v22H280z"/><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#28e746830337961c5de40b87c99980a6"><text x="350" y="44">LOWERCASELETTER</text></a></g><path d="M420 40h20m-180 0a10 10 0 0 1 10 10v10a10 10 0 0 0 10 10" transform="translate(.5 .5)"/><g class="non-terminal" transform="translate(.5 .5)"><path d="M280 59h140v22H280z"/><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#c857e06a9c6dcbcfd625e4859380c98e"><text x="350" y="74">UPPERCASELETTER</text></a></g><path d="M420 70a10 10 0 0 0 10-10V50a10 10 0 0 1 10-10m-180 0a10 10 0 0 1 10 10v40a10 10 0 0 0 10 10" transform="translate(.5 .5)"/><g class="non-terminal" transform="translate(.5 .5)"><path d="M280 100h20m100 0h20M300 89h100v22H300z"/><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#a058c5dc734e54ff3d93b96acac339f4"><text x="350" y="104">UNDERSCORE</text></a></g><path d="M420 100a10 10 0 0 0 10-10V50a10 10 0 0 1 10-10m-180 0a10 10 0 0 1 10 10v70a10 10 0 0 0 10 10" transform="translate(.5 .5)"/><g class="non-terminal" transform="translate(.5 .5)"><path d="M280 130h40m60 0h40m-100-11h60v22h-60z"/><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#999bbbf1d86bc7611397c77222e076db"><text x="350" y="134">DIGIT</text></a></g><path d="M420 130a10 10 0 0 0 10-10V50a10 10 0 0 1 10-10m-180 0a10 10 0 0 1 10 10v100a10 10 0 0 0 10 10" transform="translate(.5 .5)"/><g class="non-terminal" transform="translate(.5 .5)"><path d="M280 160h40m60 0h40m-100-11h60v22h-60z"/><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#646da671ca01bb5d84dbb5fb2238dc8e"><text x="350" y="164">SLASH</text></a></g><path d="M420 160a10 10 0 0 0 10-10V50a10 10 0 0 1 10-10m0 0h10m-190 0a10 10 0 0 0-10 10v119a10 10 0 0 0 10 10m0 0h180m0 0a10 10 0 0 0 10-10V50a10 10 0 0 0-10-10m10 0h20m0 0h10m0 0h20m-10-10v20m10-20v20" transform="translate(.5 .5)"/></svg>

* Variables starts with a **upper-case** letter
* The underscore ( `_` ) which defines a _garbage can_, than can be used if the content of the variable is not needed anymore


---
## AOP - Events, Plan, Goals

##### Plans are like _static methods_ or _functions_ in an imperative programming language, but have got a execution condition and boolean return value
```java
public static boolean dosomething( int x )
{
	if (x < 1)
    	return false;
        
    System.out.println( "I do a first thing" );
    System.out.println( "I do a second thing" );
    return true;
}
```

##### The plan condition is _optional_ and 




---
### Plan

<svg class="railroad-diagram" viewBox="0 0 726 80" id="svg_5fc25157650d0cb24f02216d904584df"><path d="M20 30v20m10-20v20M20 40h20.5m-.5 0h10m0 0a10 10 0 0 0 10-10 10 10 0 0 1 10-10m0 0h108m0 0a10 10 0 0 1 10 10 10 10 0 0 0 10 10M50 40h20" transform="translate(.5 .5)"/><g class="non-terminal" transform="translate(.5 .5)"><path d="M70 29h108v22H70z"/><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#4ab6864fc58ecd8b598ee10dfe2ac311"><text x="124" y="44">annotations</text></a></g><path d="M178 40h20m0 0h10" transform="translate(.5 .5)"/><g class="non-terminal" transform="translate(.5 .5)"><path d="M208 29h116v22H208z"/><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#4f0fa1b5875427a602b3f913163be2ca"><text x="266" y="44">plan_trigger</text></a></g><path d="M324 40h10m0 0h10" transform="translate(.5 .5)"/><g class="non-terminal" transform="translate(.5 .5)"><path d="M344 29h76v22h-76z"/><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#f0d674f1e0ed4292267f149c5983db02"><text x="382" y="44">literal</text></a></g><path d="M420 40h10m0 0a10 10 0 0 0 10-10 10 10 0 0 1 10-10m0 0h152m0 0a10 10 0 0 1 10 10 10 10 0 0 0 10 10m-192 0h20m0 0h10" transform="translate(.5 .5)"/><g class="non-terminal" transform="translate(.5 .5)"><path d="M460 29h132v22H460z"/><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#d60b4a42e52668da3017e5717ef3f60"><text x="526" y="44">plandefinition</text></a></g><path d="M592 40h10m-142 0a10 10 0 0 0-10 10 10 10 0 0 0 10 10m0 0h132m0 0a10 10 0 0 0 10-10 10 10 0 0 0-10-10m10 0h20m0 0h10" transform="translate(.5 .5)"/><g class="non-terminal" transform="translate(.5 .5)"><path d="M632 29h44v22h-44z"/><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#40679521b5da0954b705341a2859f782"><text x="654" y="44">DOT</text></a></g><path d="M676 40h10m0 0h20m-10-10v20m10-20v20" transform="translate(.5 .5)"/></svg>


---
### Goal

<svg height=125px class="railroad-diagram" viewBox="0 0 350 92" id="svg_37e402d5ccf7ec7d8ed9ebd1d8f2fe97"><path d="M20 21v20m10-20v20M20 31h20.5m-.5 0h10m0 0h20" transform="translate(.5 .5)"/><g class="non-terminal" transform="translate(.5 .5)"><path d="M70 31h4m52 0h4M74 20h52v22H74z"/><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#883acd43c77567e1c3baced84ccf6ed7"><text x="100" y="35">PLUS</text></a></g><path d="M130 31h20M50 31a10 10 0 0 1 10 10v10a10 10 0 0 0 10 10" transform="translate(.5 .5)"/><g class="non-terminal" transform="translate(.5 .5)"><path d="M70 50h60v22H70z"/><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#ffc0d9b54a1fe677c4c9e6b050e67c81"><text x="100" y="65">MINUS</text></a></g><path d="M130 61a10 10 0 0 0 10-10V41a10 10 0 0 1 10-10m0 0h10" transform="translate(.5 .5)"/><g class="non-terminal" transform="translate(.5 .5)"><path d="M160 20h140v22H160z"/><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#a811f517fa7f9ba04cf05d3a6c777799"><text x="230" y="35">EXCLAMATIONMARK</text></a></g><path d="M300 31h10m0 0h20m-10-10v20m10-20v20" transform="translate(.5 .5)"/></svg>


#### Example

```prolog
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


---
### Belief

<svg height=125px class="railroad-diagram" viewBox="0 0 180 92" id="svg_858bcfe2e941e450adf21315fc0aa172"><path d="M20 21v20m10-20v20M20 31h20.5m-.5 0h20" transform="translate(.5 .5)"/><g class="non-terminal" transform="translate(.5 .5)"><path d="M60 31h4m52 0h4M64 20h52v22H64z"/><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#883acd43c77567e1c3baced84ccf6ed7"><text x="90" y="35">PLUS</text></a></g><path d="M120 31h20M40 31a10 10 0 0 1 10 10v10a10 10 0 0 0 10 10" transform="translate(.5 .5)"/><g class="non-terminal" transform="translate(.5 .5)"><path d="M60 50h60v22H60z"/><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#ffc0d9b54a1fe677c4c9e6b050e67c81"><text x="90" y="65">MINUS</text></a></g><path d="M120 61a10 10 0 0 0 10-10V41a10 10 0 0 1 10-10m0 0h20m-10-10v20m10-20v20" transform="translate(.5 .5)"/></svg>


---
## AOP - Agent Cycle

Agents are executed by a runtime of the simulation and do the following steps:

1. can update all beliefs
	perceives the environment with the sensors
2. executes all possible plans
	do something with the sensor data
    and follows-up other plans from the preceding cycle

> __But each step is done in a parallel execution, so all data of the sensors are collected in parallel and all possible plans are run in parallel and all agents work in parallel__


---
## Execution - Finite-State-Machine


---
## Execution - Unification

<svg class="railroad-diagram" viewBox="0 0 1052 101" id="svg_e732ce4bb8479dc479e294d62beaf1cf"><path d="M20 30v20m10-20v20M20 40h20.5m-.5 0h10m0 0a10 10 0 0 0 10-10 10 10 0 0 1 10-10m0 0h36m0 0a10 10 0 0 1 10 10 10 10 0 0 0 10 10m-76 0h20" transform="translate(.5 .5)"/><g class="non-terminal" transform="translate(.5 .5)"><path d="M70 29h36v22H70z"/><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#fa868488740aa25870ced6b9169951fb"><text x="88" y="44">AT</text></a></g><path d="M106 40h20m0 0h10" transform="translate(.5 .5)"/><g class="non-terminal" transform="translate(.5 .5)"><path d="M136 29h100v22H136z"/><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#f2160f407f56e0f4d495cecd44055e2d"><text x="186" y="44">RIGHTSHIFT</text></a></g><path d="M236 40h10m0 0h20" transform="translate(.5 .5)"/><g class="non-terminal" transform="translate(.5 .5)"><path d="M266 40h320m76 0h320M586 29h76v22h-76z"/><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#f0d674f1e0ed4292267f149c5983db02"><text x="624" y="44">literal</text></a></g><path d="M982 40h20m-756 0a10 10 0 0 1 10 10v10a10 10 0 0 0 10 10m716 0" transform="translate(.5 .5)"/><g class="non-terminal" transform="translate(.5 .5)"><path d="M266 59h148v22H266z"/><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#5ffa5d1c78ad09c7bf5b4d0b0764641f"><text x="340" y="74">LEFTROUNDBRACKET</text></a></g><path d="M414 70h10m0 0h10" transform="translate(.5 .5)"/><g class="non-terminal" transform="translate(.5 .5)"><path d="M434 59h76v22h-76z"/><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#f0d674f1e0ed4292267f149c5983db02"><text x="472" y="74">literal</text></a></g><path d="M510 70h10m0 0h10" transform="translate(.5 .5)"/><g class="non-terminal" transform="translate(.5 .5)"><path d="M530 59h60v22h-60z"/><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#4d9b3e9fc12849d060371eb65154c751"><text x="560" y="74">COMMA</text></a></g><path d="M590 70h10m0 0h10" transform="translate(.5 .5)"/><g class="non-terminal" transform="translate(.5 .5)"><path d="M610 59h196v22H610z"/><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#89368367b9f48fd82a781f5a4e1ad8b6"><text x="708" y="74">unification_constraint</text></a></g><path d="M806 70h10m0 0h10" transform="translate(.5 .5)"/><g class="non-terminal" transform="translate(.5 .5)"><path d="M826 59h156v22H826z"/><a xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#3a52152b9f1e9dd45998ce24723d98ed"><text x="904" y="74">RIGHTROUNDBRACKET</text></a></g><path d="M982 70a10 10 0 0 0 10-10V50a10 10 0 0 1 10-10m0 0h10m0 0h20m-10-10v20m10-20v20" transform="translate(.5 .5)"/></svg>


---
## Theory - Close- and Open-World-Assumption


---
## Theory - Architecture


---
## Exercise - Agent Example


---
## Exercise - Concepts and Hints

* A full list of all syntax elements of the programming language can be found in the [agent syntax diagram](http://lightjason.github.io/AgentSpeak/rrd-output/html/org/lightjason/agentspeak/grammar/Agent.g4/)

