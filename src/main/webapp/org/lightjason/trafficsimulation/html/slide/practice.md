<!-- $theme: default -->
<!-- $size: 16:9 -->

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

## Basic Knowledge - Agent Cycle

Agents are executed by the runtime of the simulation and do the following two steps:

1. can update all beliefs
	perceives the evnironment with the sensors
2. executes all possible plans
	do something with the sensor data
    and follows-up other plans from the preceding cycle

> __But each step is done in a parallel execution, so all data of the sensors are collection in parallel and all possible plans are run in parallel and all agents work in parallel__

---

## Basics - Beliefs

The agent needs to store data, the _literals_ - also named _facts_ -

---

## Basics - Plans

---

## Basics - Unification

---

## Basics - Close- and Open-World-Assumption

---

## Basics - Finite-State-Machine

---

## Basics - Actions

https://lightjason.github.io/knowledgebase/actions/

---

## Background - Architecture

---

## Agent Example




---

## Scenario

---

## Helpful Concepts and Hints

---

## Challange
