<!-- $theme: default -->
<!-- $size: 16:9 -->


# Course & Simulation Lab 
## _Modelling and simulation of socio-technical systems_

Prof. Dr. Jörg P. Müller, Dipl. Inf. Philipp Kraus 
TU Clausthal
September 19-20, 2017

---
<!-- page_number: true -->

## Objectives of this course

*   Traffic systems are complex socio-technical systems
*   Multi-agent systems: Important microscopic approach to model and simulate these kinds of systems
*   We want you to
    *   understand the challenges in analysing, modelling, designing socio-technical systems
    *   gain some understanding of the core concepts of intelligent agents and multi-agent systems as a modelling paradigm for STS
   	*   in this context: understand basic models of algorithmic game theory and collective decision making mechanisms
    *   keep you from falling asleep by allowing you to practically explore basic concepts and tools of agent/MAS modelling and simulation using the agent-based programming language LightJason

---
## Objectives of this course

<div style="text-align:center">

<span>![image](slides/sociotechnicalsystems/image_003.png)</span>

</div>

<div style="text-align:left">

While doing all this, keep at a level which can be understood and mastered by non-Computer Scientists
</div>
    
---
   
## Course agenda overview


<table class=TableNormal border=0 cellspacing=0 cellpadding=0
     style='border-collapse:collapse'>
     <tr style='height:40.8pt'>
      <td valign=top style='width:308pt; border:solid black 1.0pt;
      background:#F2F2F2;padding:0cm 0cm 0cm 0cm;height:20.8pt' class="auto-style2">
      <p class=TableParagraph style='margin-top:3.8pt;margin-right:62.2pt;
      margin-bottom:0cm;margin-left:62.0pt;margin-bottom:.0001pt;text-indent:
      15.7pt;line-height:18.0pt'><span lang=EN-US style='font-size:15.0pt;
      letter-spacing:-.2pt'>Tuesday,</span><span style='font-size:15.0pt;
      letter-spacing:-.2pt'> </span><span lang=EN-US
      style='font-size:15.0pt'>September<span style='letter-spacing:-.55pt'> </span>19</span></p>
      </td>
      <td valign=top style='border-right: 1.0pt solid black; border-top: 1.0pt solid black; border-bottom: 1.0pt solid black; width:322pt; background:#F2F2F2; padding:0cm 0cm 0cm 0cm; height:20.8pt; border-left-style: none; border-left-color: inherit; border-left-width: medium;' class="auto-style2">
      <p class=auto-style1 style='margin-top:3.8pt;margin-right:61.95pt;
      margin-bottom:0cm;margin-left:62.1pt;margin-bottom:.0001pt;text-indent:
      5.15pt;line-height:18.0pt'><span lang=EN-US style='font-size:15.0pt;
      letter-spacing:-.15pt'>Wednesday, </span><span lang=EN-US
      style='font-size:15.0pt'>September<span style='letter-spacing:-.55pt'> </span>20</span></p>
      </td>
     </tr>
     <tr style='height:103.55pt'>
      <td valign=top style='border-left: 1.0pt solid black; border-right: 1.0pt solid black; border-bottom: 1.0pt solid black; width:308pt; background:#FDE9D9; padding:0cm 0cm 0cm 0cm; height:103.55pt; border-top-style: none; border-top-color: inherit; border-top-width: medium;' class="auto-style1">
      <p class=TableParagraph align=center style='margin-top:.05pt;margin-right:
      .05pt;margin-bottom:0cm;margin-left:0cm;margin-bottom:.0001pt;text-align:
      center;line-height:16.95pt'><b><span lang=EN-US style='font-size:14.0pt'>10:00-12:00</span></b></p>
      <p class=TableParagraph align=center style='margin-right:.1pt;text-align:
      center;line-height:16.95pt'><b><span lang=EN-US style='font-size:14.0pt'>Lecture<span
      style='letter-spacing:2.65pt'> </span>1</span></b></p>
      <p class=TableParagraph align=center style='margin-top:0cm;margin-right:
      11.1pt;margin-bottom:0cm;margin-left:11.1pt;margin-bottom:.0001pt;
      text-align:center;line-height:16.8pt'><span lang=EN-US style='font-size:
      14.0pt'>Intelligent agents and<span style='letter-spacing:-1.2pt'> </span>Multi-agent
      systems: <br />
	  An<span style='letter-spacing:-1.5pt'> </span>introduction</span></p>
      </td>
      <td valign=top style='width:322pt; border-bottom:solid black 1.0pt;border-right:solid black 1.0pt;
      background:#DCE6F1;padding:0cm 0cm 0cm 0cm;height:103.55pt; border-left-style: none; border-left-color: inherit; border-left-width: medium; border-top-style: none; border-top-color: inherit; border-top-width: medium;' class="auto-style1">
      <p class=TableParagraph align=center style='margin-top:.3pt;margin-right:
      .2pt;margin-bottom:0cm;margin-left:0cm;margin-bottom:.0001pt;text-align:
      center;line-height:16.95pt'><b><span lang=EN-US style='font-size:14.0pt'>9:00-12:00</span></b></p>
      <p class=TableParagraph align=center style='margin-right:.25pt;
      text-align:center;line-height:16.95pt'><b><span lang=EN-US
      style='font-size:14.0pt'>Simulation Lab<span style='letter-spacing:-.45pt'>
      </span>2</span></b></p>
      <p class=TableParagraph align=center style='margin-top:0cm;margin-right:
      26.4pt;margin-bottom:0cm;margin-left:26.1pt;margin-bottom:.0001pt;
      text-align:center;line-height:16.8pt'><span lang=EN-US style='font-size:
      14.0pt'>Multi-agent-based<span style='letter-spacing:-.4pt'> </span>modeling
      with<span style='letter-spacing:-.4pt'> </span>LightJason</span></p>
      </td>
     </tr>
     <tr style='height:34.45pt'>
      <td valign=top style='border-left: 1.0pt solid black; border-right: 1.0pt solid black; border-bottom: 1.0pt solid black; width:308pt; background:#EBF1DE; padding:0cm 0cm 0cm 0cm; height:20px; border-top-style: none; border-top-color: inherit; border-top-width: medium;' class="auto-style1">
      <p class=TableParagraph align=center style='margin-top:.2pt;margin-right:
      .05pt;margin-bottom:0cm;margin-left:0cm;margin-bottom:.0001pt;text-align:
      center;line-height:16.95pt'><b><span lang=EN-US style='font-size:14.0pt'>12:00-13:15</span></b></p>
      <p class=TableParagraph align=center style='margin-right:.05pt;
      text-align:center;line-height:16.95pt'><span lang=EN-US style='font-size:
      14.0pt'>Lunch<span style='letter-spacing:-.55pt'> </span>Break</span></p>
      </td>
      <td valign=top style='width:322pt; border-bottom:solid black 1.0pt;border-right:solid black 1.0pt;
      background:#EBF1DE;padding:0cm 0cm 0cm 0cm;height:20px; border-left-style: none; border-left-color: inherit; border-left-width: medium; border-top-style: none; border-top-color: inherit; border-top-width: medium;' class="auto-style1">
      <p class=TableParagraph align=center style='margin-top:.2pt;margin-right:
      .1pt;margin-bottom:0cm;margin-left:0cm;margin-bottom:.0001pt;text-align:
      center;line-height:16.95pt'><b><span lang=EN-US style='font-size:14.0pt'>12:00-13:15</span></b></p>
      <p class=TableParagraph align=center style='margin-right:.1pt;text-align:
      center;line-height:16.95pt'><span lang=EN-US style='font-size:14.0pt'>Lunch<span
      style='letter-spacing:-.55pt'> </span>Break</span></p>
      </td>
     </tr>
     <tr style='height:86.15pt'>
      <td valign=top style='border-left: 1.0pt solid black; border-right: 1.0pt solid black; border-bottom: 1.0pt solid black; width:308pt; background:#DCE6F1; padding:0cm 0cm 0cm 0cm; height:86.15pt; border-top-style: none; border-top-color: inherit; border-top-width: medium;' class="auto-style1">
      <p class=TableParagraph align=center style='margin-top:.2pt;margin-right:
      .05pt;margin-bottom:0cm;margin-left:0cm;margin-bottom:.0001pt;text-align:
      center;line-height:16.95pt'><b><span lang=EN-US style='font-size:14.0pt'>13:15-15:00</span></b></p>
      <p class=auto-style1 style='margin-left:56.95pt;line-height:16.95pt'><b><span
      lang=EN-US style='font-size:14.0pt'>Simulation Lab<span style='letter-spacing:
      -.45pt'> </span>1</span></b></p>
      <p class=TableParagraph align=center style='margin-top:0cm;margin-right:
      12.05pt;margin-bottom:0cm;margin-left:11.95pt;margin-bottom:.0001pt;
      text-align:center;line-height:16.8pt'><span lang=EN-US style='font-size:
      14.0pt'>Multi-agent-based modeling<span style='letter-spacing:-.4pt'> </span>with
      LightJason</span></p>
      </td>
      <td valign=top style='width:322pt; border-bottom:solid black 1.0pt;border-right:solid black 1.0pt;
      background:#FDE9D9;padding:0cm 0cm 0cm 0cm;height:86.15pt; border-left-style: none; border-left-color: inherit; border-left-width: medium; border-top-style: none; border-top-color: inherit; border-top-width: medium;' class="auto-style1">
      <p class=TableParagraph align=center style='margin-top:.2pt;margin-right:
      .1pt;margin-bottom:0cm;margin-left:0cm;margin-bottom:.0001pt;text-align:
      center;line-height:16.95pt'><b><span lang=EN-US style='font-size:14.0pt'>13:15-16:00</span></b></p>
      <p class=TableParagraph align=center style='margin-right:.2pt;text-align:
      center;line-height:16.95pt'><b><span lang=EN-US style='font-size:14.0pt'>Lecture<span
      style='letter-spacing:2.65pt'> </span>3</span></b></p>
      <p class=TableParagraph align=center style='margin-top:0cm;margin-right:
      52.35pt;margin-bottom:0cm;margin-left:52.15pt;margin-bottom:.0001pt;
      text-align:center;text-indent:.1pt;line-height:16.8pt'><span lang=EN-US
      style='font-size:14.0pt'>Algorithmic mechanism<span style='letter-spacing:
      -.45pt'> </span>design</span></p>
      </td>
     </tr>
     <tr style='height:34.55pt'>
      <td valign=top style='border-left: 1.0pt solid black; border-right: 1.0pt solid black; border-bottom: 1.0pt solid black; width:308pt; background:#EBF1DE; padding:0cm 0cm 0cm 0cm; height:20px; border-top-style: none; border-top-color: inherit; border-top-width: medium;' class="auto-style1">
      <p class=TableParagraph align=center style='margin-top:.3pt;margin-right:
      .05pt;margin-bottom:0cm;margin-left:0cm;margin-bottom:.0001pt;text-align:
      center;line-height:16.95pt'><b><span lang=EN-US style='font-size:14.0pt'>15:00-15:15</span></b></p>
      <p class=TableParagraph align=center style='margin-right:.1pt;text-align:
      center;line-height:16.95pt'><span lang=EN-US style='font-size:14.0pt'>Coffee<span
      style='letter-spacing:-.95pt'> </span>Break</span></p>
      </td>
      <td valign=top style='width:322pt; border-bottom:solid black 1.0pt;border-right:solid black 1.0pt;
      background:#EBF1DE;padding:0cm 0cm 0cm 0cm;height:20px; border-left-style: none; border-left-color: inherit; border-left-width: medium; border-top-style: none; border-top-color: inherit; border-top-width: medium;' class="auto-style1">
      <p class=TableParagraph align=center style='margin-top:.3pt;margin-right:
      .1pt;margin-bottom:0cm;margin-left:0cm;margin-bottom:.0001pt;text-align:
      center;line-height:16.95pt'><b><span lang=EN-US style='font-size:14.0pt'>16:00-16:15</span></b></p>
      <p class=TableParagraph align=center style='margin-right:.2pt;text-align:
      center;line-height:16.95pt'><span lang=EN-US style='font-size:14.0pt'>Coffee<span
      style='letter-spacing:-.95pt'> </span>Break</span></p>
      </td>
     </tr>
     <tr style='height:86.15pt'>
      <td valign=top style='border-left: 1.0pt solid black; border-right: 1.0pt solid black; border-bottom: 1.0pt solid black; width:308pt; background:#FDE9D9; padding:0cm 0cm 0cm 0cm; height:86.15pt; border-top-style: none; border-top-color: inherit; border-top-width: medium;' class="auto-style1">
      <p class=TableParagraph align=center style='margin-top:.2pt;margin-right:
      .05pt;margin-bottom:0cm;margin-left:0cm;margin-bottom:.0001pt;text-align:
      center;line-height:16.95pt'><b><span lang=EN-US style='font-size:14.0pt'>15:15-17:00</span></b></p>
      <p class=TableParagraph align=center style='margin-right:.1pt;text-align:
      center;line-height:16.95pt'><b><span lang=EN-US style='font-size:14.0pt'>Lecture<span
      style='letter-spacing:-.45pt'> </span>2</span></b></p>
      <p class=TableParagraph align=center style='text-align:center'><span
      lang=EN-US style='font-size:14.0pt'>Coordination and game<span
      style='letter-spacing:-.9pt'> </span>theory</span></p>
      </td>
      <td valign=top style='width:322pt; border-bottom:solid black 1.0pt;border-right:solid black 1.0pt;
      background:#F2F2F2;padding:0cm 0cm 0cm 0cm;height:86.15pt; border-left-style: none; border-left-color: inherit; border-left-width: medium; border-top-style: none; border-top-color: inherit; border-top-width: medium;' class="auto-style1">
      <p class=TableParagraph align=center style='margin-top:.2pt;margin-right:
      .1pt;margin-bottom:0cm;margin-left:0cm;margin-bottom:.0001pt;text-align:
      center'><b><span lang=EN-US style='font-size:14.0pt'>16:15-17:00</span></b></p>
      <p class=auto-style1 style='margin-top:.55pt'><span lang=EN-US
      style='font-size:13.0pt'></span></p>
      <p class=TableParagraph align=center style='margin-top:0cm;margin-right:
      55.15pt;margin-bottom:0cm;margin-left:55.05pt;margin-bottom:.0001pt;
      text-align:center;line-height:16.8pt'><span lang=EN-US style='font-size:
      14.0pt'>Plenary<span style='letter-spacing:-.55pt'> </span>Feedback and<span
      style='letter-spacing:.2pt'> </span><span style='letter-spacing:-.15pt'>Wrap-Up</span></span></p>
      </td>
     </tr>
    </table>



---
    
## Lecture 1
## _INTELLIGENT AGENTS AND MULTI-AGENT SYSTEMS: AN INTRODUCTION_
    
---    
## Outline Lecture 1

*   Motivation and basic definitions

*   Socio-technical systems

*   Example: vehicle agent

*   Agent-based modelling and simulation

*   Agent architectures

    *   Reactive

    *   Ant-based

    *   Practical reasoning

    *   Belief, Desire, Intention

---

## What is this about?
We aim at understanding, modelling, building complex, ”intelligent” autonomous systems
So what? – humans have been doing this for a long time …
*   James Watt – Steam Engine
*   Weather forecast
*   Traffic flows and traffic forecast
*   Energy network management
*   Finance markets …

But
*   Often unclear system boundaries
*   What if systems are not hierarchically, centrally organized?
*   Often simplifying assumptions regarding behaviour of system parts
*   Forecasting the weather doesn’t change it, forecasting traffic …?
*   Often, it doesn’t work properly

---
## Socio-technical systems (STS) (Singh, 2014)
Combine (information) technology with real-life societal considerations
System characteristics
*   Longevity and openness
*   Societal requirements, often characterized by norms
*   Indirect ”control” mechanisms (sanctions, incentives, markets, …)

---
## Socio-technical systems (STS) (Singh, 2014)
Member characteristics
*   Longevity and identity
*   Human or automated (humans not as users, but as part of a system)
*   Autonomy (goals, capabilities) and heterogeneity

Realization
*   Top-down: Members fit into existing systems
	*   Adopt suitable goals given system norms
*   Bottom up: Members design new systems
	*   Negotiate suitable norms given individual goals

---
## The ”3M” approach to modelling socio-technical systems
<span>![image](slides/sociotechnicalsystems/image_004.png)</span>


---
## Traffic/Smart City Systems are STS!
*   Longlived infrastructure

*   Self-interested, autonomous traffic participants

*   Highly heterogeneous

*   Societal issues: Efficiency, safety, comfort, environmental friendliness, fairness, sustainability

*   Decentral decisions vs. centralized (?) regulation

*   New technologies (automated driving, social networks,

    digitalization)

    *   influence behaviour of traffic participants

    *   enable new services, business models

---
## Research challenges: understand how


<table>
	<tr>
		<td style="height: 21px">&nbsp;… new technologies and services change 
		the behaviour of traffic systems <br><br>… they interact with <strong>
		human traffic participants </strong>and <strong>traffic management</strong><br>
		<br>… we can support cooperative traffic management and urban mobility 
		solutions through appropriate models and methods <br><br>… we can 
		advance towards abstractions and methods for modelling, analysing, 
		predicting, controlling, engineering complex socio- technical systems </td>
		<td style="height: 21px">
		<img src="image_006.png" ></td>
	</tr>
</table>

---
## Example: Pedestrian Modelling

Traditional pedestrian models:
*   pedestrians = homogeneous entities (molecules)
*   physical (fluid) dynamics models to describe flows

Social behaviour: repelling forces between molecules
E.g. Social forces model (Helbing and Molnar,1995)

---
## Example: Pedestrian Modelling
<span>![image](slides/sociotechnicalsystems/image_012.png)</span>

-> Highly simplified model – can model large problems!
-> More complex individual goals and motivations?
-> Heterogeneous individual capabilities?</span>
 
 
---
## Example: Nagel-Schreckenberg Car Following Model (Nagel and Schreckenberg, 1992)

<table cellpadding="0" cellspacing="0">
	<tr>
		<td>
		<div style="language:en-GB;margin-top:5.28pt;margin-bottom:0pt;margin-left:
.21in;text-indent:-.21in;text-align:left;direction:ltr;unicode-bidi:embed;
vertical-align:baseline;mso-line-break-override:none;punctuation-wrap:hanging">
			<span style="font-size:
22.0pt;font-family:Arial;mso-ascii-font-family:Arial;mso-fareast-font-family:
+mn-ea;mso-bidi-font-family:Arial;mso-fareast-theme-font:minor-fareast;
color:black;mso-color-index:1;language:de;mso-style-textfill-type:solid;
mso-style-textfill-fill-themecolor:text1;mso-style-textfill-fill-color:black;
mso-style-textfill-fill-alpha:100.0%">Microscopic, </span>
			<span style="font-size:22.0pt;
font-family:Arial;mso-ascii-font-family:Arial;mso-fareast-font-family:+mn-ea;
mso-bidi-font-family:Arial;mso-fareast-theme-font:minor-fareast;color:black;
mso-color-index:1;language:de;mso-style-textfill-type:solid;mso-style-textfill-fill-themecolor:
text1;mso-style-textfill-fill-color:black;mso-style-textfill-fill-alpha:100.0%">
			discrete</span><span style="font-size:22.0pt;font-family:Arial;mso-ascii-font-family:Arial;
mso-fareast-font-family:+mn-ea;mso-bidi-font-family:Arial;mso-fareast-theme-font:
minor-fareast;color:black;mso-color-index:1;language:de;mso-style-textfill-type:
solid;mso-style-textfill-fill-themecolor:text1;mso-style-textfill-fill-color:
black;mso-style-textfill-fill-alpha:100.0%"> </span>
			<span style="font-size:
22.0pt;font-family:Arial;mso-ascii-font-family:Arial;mso-fareast-font-family:
+mn-ea;mso-bidi-font-family:Arial;mso-fareast-theme-font:minor-fareast;
color:black;mso-color-index:1;language:de;mso-style-textfill-type:solid;
mso-style-textfill-fill-themecolor:text1;mso-style-textfill-fill-color:black;
mso-style-textfill-fill-alpha:100.0%">model</span><span style="font-size:22.0pt;
font-family:Arial;mso-ascii-font-family:Arial;mso-fareast-font-family:+mn-ea;
mso-bidi-font-family:Arial;mso-fareast-theme-font:minor-fareast;color:black;
mso-color-index:1;language:de;mso-style-textfill-type:solid;mso-style-textfill-fill-themecolor:
text1;mso-style-textfill-fill-color:black;mso-style-textfill-fill-alpha:100.0%">
			</span></div>
		<div class="O1" style="language:en-GB;margin-top:4.56pt;margin-bottom:0pt;
margin-left:.64in;text-indent:-.14in;text-align:left;direction:ltr;unicode-bidi:
embed;vertical-align:baseline;mso-line-break-override:none;punctuation-wrap:
hanging">
			<span style="font-size:19.0pt;font-family:Arial;
mso-ascii-font-family:Arial;mso-bidi-font-family:Arial;color:black;mso-color-index:
1;language:de;mso-style-textfill-type:solid;mso-style-textfill-fill-themecolor:
text1;mso-style-textfill-fill-color:black;mso-style-textfill-fill-alpha:100.0%">
			based</span><span style="font-size:19.0pt;font-family:Arial;mso-ascii-font-family:Arial;
mso-bidi-font-family:Arial;color:black;mso-color-index:1;language:de;
mso-style-textfill-type:solid;mso-style-textfill-fill-themecolor:text1;
mso-style-textfill-fill-color:black;mso-style-textfill-fill-alpha:100.0%"> on 
			cellular automata</span></div>
		<div class="O1" style="language:en-GB;margin-top:4.56pt;margin-bottom:0pt;
margin-left:.64in;text-indent:-.14in;text-align:left;direction:ltr;unicode-bidi:
embed;vertical-align:baseline;mso-line-break-override:none;punctuation-wrap:
hanging">
			<span style="font-size:19.0pt;font-family:Arial;
mso-ascii-font-family:Arial;mso-bidi-font-family:Arial;color:black;mso-color-index:
1;language:en-GB;mso-style-textfill-type:solid;mso-style-textfill-fill-themecolor:
text1;mso-style-textfill-fill-color:black;mso-style-textfill-fill-alpha:100.0%">
			car behaviour determined by the state of preceding car</span></div>
		<div style="language:en-GB;margin-top:5.28pt;margin-bottom:0pt;margin-left:
.21in;text-indent:-.21in;text-align:left;direction:ltr;unicode-bidi:embed;
vertical-align:baseline;mso-line-break-override:none;punctuation-wrap:hanging">
			<span style="font-size:
22.0pt;font-family:Arial;mso-ascii-font-family:Arial;mso-fareast-font-family:
+mn-ea;mso-bidi-font-family:Arial;mso-fareast-theme-font:minor-fareast;
color:black;mso-color-index:1;language:de;mso-style-textfill-type:solid;
mso-style-textfill-fill-themecolor:text1;mso-style-textfill-fill-color:black;
mso-style-textfill-fill-alpha:100.0%">Use </span>
			<span style="font-size:22.0pt;
font-family:Arial;mso-ascii-font-family:Arial;mso-fareast-font-family:+mn-ea;
mso-bidi-font-family:Arial;mso-fareast-theme-font:minor-fareast;color:black;
mso-color-index:1;language:de;mso-style-textfill-type:solid;mso-style-textfill-fill-themecolor:
text1;mso-style-textfill-fill-color:black;mso-style-textfill-fill-alpha:100.0%">
			four</span><span style="font-size:22.0pt;font-family:Arial;mso-ascii-font-family:Arial;
mso-fareast-font-family:+mn-ea;mso-bidi-font-family:Arial;mso-fareast-theme-font:
minor-fareast;color:black;mso-color-index:1;language:de;mso-style-textfill-type:
solid;mso-style-textfill-fill-themecolor:text1;mso-style-textfill-fill-color:
black;mso-style-textfill-fill-alpha:100.0%"> </span>
			<span style="font-size:
22.0pt;font-family:Arial;mso-ascii-font-family:Arial;mso-fareast-font-family:
+mn-ea;mso-bidi-font-family:Arial;mso-fareast-theme-font:minor-fareast;
color:black;mso-color-index:1;language:de;mso-style-textfill-type:solid;
mso-style-textfill-fill-themecolor:text1;mso-style-textfill-fill-color:black;
mso-style-textfill-fill-alpha:100.0%">rules</span><span style="font-size:22.0pt;
font-family:Arial;mso-ascii-font-family:Arial;mso-fareast-font-family:+mn-ea;
mso-bidi-font-family:Arial;mso-fareast-theme-font:minor-fareast;color:black;
mso-color-index:1;language:de;mso-style-textfill-type:solid;mso-style-textfill-fill-themecolor:
text1;mso-style-textfill-fill-color:black;mso-style-textfill-fill-alpha:100.0%"> 
			in </span>
			<span style="font-size:22.0pt;font-family:Arial;mso-ascii-font-family:
Arial;mso-fareast-font-family:+mn-ea;mso-bidi-font-family:Arial;mso-fareast-theme-font:
minor-fareast;color:black;mso-color-index:1;language:de;mso-style-textfill-type:
solid;mso-style-textfill-fill-themecolor:text1;mso-style-textfill-fill-color:
black;mso-style-textfill-fill-alpha:100.0%">each</span><span style="font-size:
22.0pt;font-family:Arial;mso-ascii-font-family:Arial;mso-fareast-font-family:
+mn-ea;mso-bidi-font-family:Arial;mso-fareast-theme-font:minor-fareast;
color:black;mso-color-index:1;language:de;mso-style-textfill-type:solid;
mso-style-textfill-fill-themecolor:text1;mso-style-textfill-fill-color:black;
mso-style-textfill-fill-alpha:100.0%"> </span>
			<span style="font-size:22.0pt;
font-family:Arial;mso-ascii-font-family:Arial;mso-fareast-font-family:+mn-ea;
mso-bidi-font-family:Arial;mso-fareast-theme-font:minor-fareast;color:black;
mso-color-index:1;language:de;mso-style-textfill-type:solid;mso-style-textfill-fill-themecolor:
text1;mso-style-textfill-fill-color:black;mso-style-textfill-fill-alpha:100.0%">
			step</span></div>
		<div class="O1" style="language:en-GB;margin-top:4.56pt;margin-bottom:0pt;
margin-left:.99in;text-indent:-.5in;text-align:left;direction:ltr;unicode-bidi:
embed;vertical-align:baseline;mso-line-break-override:none;punctuation-wrap:
hanging">
			<span style="font-size:19.0pt;
font-family:Arial;mso-ascii-font-family:Arial;mso-bidi-font-family:Arial;
color:black;mso-color-index:1;language:de;mso-style-textfill-type:solid;
mso-style-textfill-fill-themecolor:text1;mso-style-textfill-fill-color:black;
mso-style-textfill-fill-alpha:100.0%">I. Increase</span><span style="font-size:
19.0pt;font-family:Arial;mso-ascii-font-family:Arial;mso-bidi-font-family:Arial;
color:black;mso-color-index:1;language:de;mso-style-textfill-type:solid;
mso-style-textfill-fill-themecolor:text1;mso-style-textfill-fill-color:black;
mso-style-textfill-fill-alpha:100.0%"> v </span>
			<span style="font-size:19.0pt;
font-family:Arial;mso-ascii-font-family:Arial;mso-bidi-font-family:Arial;
color:black;mso-color-index:1;language:de;mso-style-textfill-type:solid;
mso-style-textfill-fill-themecolor:text1;mso-style-textfill-fill-color:black;
mso-style-textfill-fill-alpha:100.0%">by 1 if possible</span></div>
		<div class="O1" style="language:en-GB;margin-top:4.56pt;margin-bottom:0pt;
margin-left:.99in;text-indent:-.5in;text-align:left;direction:ltr;unicode-bidi:
embed;vertical-align:baseline;mso-line-break-override:none;punctuation-wrap:
hanging">
			<span style="font-size:19.0pt;
font-family:Arial;mso-ascii-font-family:Arial;mso-bidi-font-family:Arial;
color:black;mso-color-index:1;language:de;mso-style-textfill-type:solid;
mso-style-textfill-fill-themecolor:text1;mso-style-textfill-fill-color:black;
mso-style-textfill-fill-alpha:100.0%">II. Reduce v by 1 if not enough free cells 
			in front</span></div>
		<div class="O1" style="language:en-GB;margin-top:4.56pt;margin-bottom:0pt;
margin-left:.99in;text-indent:-.5in;text-align:left;direction:ltr;unicode-bidi:
embed;vertical-align:baseline;mso-line-break-override:none;punctuation-wrap:
hanging">
			<span style="font-size:19.0pt;
font-family:Arial;mso-ascii-font-family:Arial;mso-bidi-font-family:Arial;
color:black;mso-color-index:1;language:de;mso-style-textfill-type:solid;
mso-style-textfill-fill-themecolor:text1;mso-style-textfill-fill-color:black;
mso-style-textfill-fill-alpha:100.0%">III. Reduce v by 1 with probability</span><span style="font-size:
19.0pt;font-family:Arial;mso-ascii-font-family:Arial;mso-bidi-font-family:Arial;
color:black;mso-color-index:1;language:de;mso-style-textfill-type:solid;
mso-style-textfill-fill-themecolor:text1;mso-style-textfill-fill-color:black;
mso-style-textfill-fill-alpha:100.0%"> p</span></div>
		<div class="O1" style="language:en-GB;margin-top:4.56pt;margin-bottom:0pt;
margin-left:.99in;text-indent:-.5in;text-align:left;direction:ltr;unicode-bidi:
embed;vertical-align:baseline;mso-line-break-override:none;punctuation-wrap:
hanging">
			<span style="font-size:19.0pt;
font-family:Arial;mso-ascii-font-family:Arial;mso-bidi-font-family:Arial;
color:black;mso-color-index:1;language:de;mso-style-textfill-type:solid;
mso-style-textfill-fill-themecolor:text1;mso-style-textfill-fill-color:black;
mso-style-textfill-fill-alpha:100.0%">IV. Drive with new v (all </span>
			<span style="font-size:
19.0pt;font-family:Arial;mso-ascii-font-family:Arial;mso-bidi-font-family:Arial;
color:black;mso-color-index:1;language:de;mso-style-textfill-type:solid;
mso-style-textfill-fill-themecolor:text1;mso-style-textfill-fill-color:black;
mso-style-textfill-fill-alpha:100.0%">vehicles)</span></div>
		<p style="language:en-GB;margin-top:5.28pt;margin-bottom:0pt;margin-left:.39in;
text-indent:-.39in;text-align:left;direction:ltr;unicode-bidi:embed;vertical-align:
baseline;mso-line-break-override:none;punctuation-wrap:hanging">
		<span style="font-size:22.0pt;font-family:Arial;mso-ascii-font-family:Arial;
mso-fareast-font-family:+mn-ea;mso-bidi-font-family:Arial;mso-fareast-theme-font:
minor-fareast;color:black;mso-color-index:1;language:de;mso-style-textfill-type:
solid;mso-style-textfill-fill-themecolor:text1;mso-style-textfill-fill-color:
black;mso-style-textfill-fill-alpha:100.0%">&nbsp;</span><span style="font-size:
22.0pt;font-family:Arial;mso-ascii-font-family:Arial;mso-fareast-font-family:
+mn-ea;mso-bidi-font-family:Arial;mso-fareast-theme-font:minor-fareast;
color:black;mso-color-index:1;language:de;mso-style-textfill-type:solid;
mso-style-textfill-fill-themecolor:text1;mso-style-textfill-fill-color:black;
mso-style-textfill-fill-alpha:100.0%">-&gt;Simple, homogeneous, <br />
		not </span>
		<span style="font-size:22.0pt;font-family:Arial;mso-ascii-font-family:
Arial;mso-fareast-font-family:+mn-ea;mso-bidi-font-family:Arial;mso-fareast-theme-font:
minor-fareast;color:black;mso-color-index:1;language:de;mso-style-textfill-type:
solid;mso-style-textfill-fill-themecolor:text1;mso-style-textfill-fill-color:
black;mso-style-textfill-fill-alpha:100.0%">very</span><span style="font-size:
22.0pt;font-family:Arial;mso-ascii-font-family:Arial;mso-fareast-font-family:
+mn-ea;mso-bidi-font-family:Arial;mso-fareast-theme-font:minor-fareast;
color:black;mso-color-index:1;language:de;mso-style-textfill-type:solid;
mso-style-textfill-fill-themecolor:text1;mso-style-textfill-fill-color:black;
mso-style-textfill-fill-alpha:100.0%"> </span>
		<span style="font-size:22.0pt;
font-family:Arial;mso-ascii-font-family:Arial;mso-fareast-font-family:+mn-ea;
mso-bidi-font-family:Arial;mso-fareast-theme-font:minor-fareast;color:black;
mso-color-index:1;language:de;mso-style-textfill-type:solid;mso-style-textfill-fill-themecolor:
text1;mso-style-textfill-fill-color:black;mso-style-textfill-fill-alpha:100.0%">
		realistic</span><span style="font-size:22.0pt;font-family:Arial;mso-ascii-font-family:Arial;
mso-fareast-font-family:+mn-ea;mso-bidi-font-family:Arial;mso-fareast-theme-font:
minor-fareast;color:black;mso-color-index:1;language:de;mso-style-textfill-type:
solid;mso-style-textfill-fill-themecolor:text1;mso-style-textfill-fill-color:
black;mso-style-textfill-fill-alpha:100.0%"> (but useful)</span></p>
		</td>
		<td>
		<img height="483" src="image_016.gif" width="316" /></td>
	</tr>
</table>


---
    
## Modelling socio-technical systems requires a paradigm change

<span>![image](slides/sociotechnicalsystems/image_018.png)</span><span>![image](slides/sociotechnicalsystems/image_017.jpg)</span><span>![image](slides/sociotechnicalsystems/image_019.png)</span>

*   Heterogeneous actors, different capabilities, preferences, goals
*   Groups … (Vizzari et al., 2015)
*   Interaction of human and automated actors
*   Flexible, changing regulatory regimes (rules, norms)

---
## (Multi-)agent-based modelling and simulation

	”… is a (computational) modelling and simulation paradigm that uses the concept of a 
    multiagent system as the basic metaphor of the simulation model” [Klügl, 2013]

* Microscopic paradigm
* Abstraction: <u>Agent</u> - fine-granular description of (autonomous) system entities (Jennings et al., 1998)
* Granularity: From cellular to cognitive

<img alt="" height="250" src="image_021.png" width="697"></p>

---

## (Multi-)agent-based modelling and simulation

*   Abstraction: <u>Multi-agent system</u>

		… a (computer) system that is designed and implemented 
        as several interacting agents
		… types of interactions include: cooperation …; coordination …; and negotiation 
        [Jennings et al, 1998]

<img alt="" height="280" src="image_022.png" width="516">

---    
## Example: Vehicle agent
<span>![image](slides/sociotechnicalsystems/image_026.png)</span> <span>![image](slides/sociotechnicalsystems/image_027.png)</span> <span>![image](slides/sociotechnicalsystems/image_028.png)</span>

*   Perceives the environment via sensors
*   Maintains a model of the state of the environment
*   Has strategic and tactical goals (speed, lane, route choice)
*   Plans and decides how to reach its goals
*   Executes actions that affect the environment
*   Communicates with driver/passenger, cars, infrastructure
*   Reacts to changes in the environment

---
## Key challenges in agent’s research (Wooldridge, 2009) 

*   Construct intelligent agents that can carry out actions autonomously on behalf of their users/owners
-> Represent knowledge & semantics of knowledge and action
-> Behave flexibly and adaptively
-> Embedded in environment (sensing & acting)
*   Construct multi-agent systems, i.e., systems consisting of agents that can interact with other agents to perform the tasks we delegate to them, and to resolve emerging goal conflicts
-> User/agent modelling, personalization</span>
-> Communication, coordination, cooperation, and negotiation</span>
-> Representing and dealing with semantic interoperability</span>


---
## Micro- and macro perspective of Agents/MAS
<span>![image](slides/sociotechnicalsystems/image_029.png)</span>
    
---
## History of intelligent agents
*   1930ies: Cybernetics - mathematical model for analogue control
*   1950ies: Physical symbol systems hypothesis (Simon and Newell)
    The ability to symbolically represent aspects of the world is a prerequisite for intelligent behaviour (use explicit logical reasoning in order to decide what to do) 
    -> <u>deliberative agents</u>
*   Since mid 1980s: Problems with symbolic reasoning lead to <u>reactive agents/</u>emergent behaviour (since 1985)
*   Since 1990ies
    *   hybrid/layered architectures reconciling deliberation and reactivity
    *   Focus on social-aware agents, cooperation & negotiation

---
## Agents and dynamic systems
Dynamic systems (control theory) consists of
    *   System (Controller)
    *   Environment

Let
    *   T points in time
    *   S possible environmental states
    *   P possible inputs (perception) to system
    *   A possible output (actions) of system

Let
    *   s(t) state of environment at time t $$\in$$  T
    *   p(t) input to system at time t $$\in$$  T
    *   a(t) output of system at time t $$\in$$  T

---> TODO LATEX <---

---
## Components of a dynamic system

<span>![image](slides/sociotechnicalsystems/image_030.png)</span>

---
## Describing the environment
*   The behaviour of the environment in dynamic systems is described by two functions

*   Actions performed by the system modify the state of the environment:
    $$s_{t+1} = f(s(t), a(t))$$

*   Outputs of the environment (signals) are considered as inputs and result directly from the current state of the environment s(t):
    $$p(t) = g(s(t))$$
    
---
## System behaviour: The control problem
*   Task of the system; solving the control problem
    = Find a sequence of actions to achieve or maintain a desired state of the     environment
*   The control problem is often described by two sub-problems:
*   The _state estimation problem_ determines the current state of the environment from the current input into the system
*   The _regulation problem_ determines an appropriate system response (action) given a recognized state of the environment
*   Functions:
    *   State recognition:  est: $$P \rightarrow S$$
    *   Regulator: 		    reg: $$S \rightarrow A$$

---
## Discussion: Agents and dynamic systems
One perspective of agents is that they solve control problems and they can be regarded dynamic systems

But:
*   Functions for state estimation and regulation are often incomplete, incorrect, or complex to describe in practical applications
*   Functions need to process symbolic knowledge and generate complex, non-numeric output description
*   We are looking at distributed systems of loosely coupled agents; control theory does not provide instruments to solve this problem

---
## Discussion: Agents and dynamic systems
Pure control theory models are rarely applicable
*   Failure-prone sensors and actuators (input to est, output of reg)
*   Incomplete, erroneous state model (output of est)
*   System/agent may be in a different state from the one it believes to be in
*   Failure-prone model of effects of actions to environment

But: useful as a basic mathematical model

---
## Minimal abstract agent model (cycle)

<span>![image](slides/sociotechnicalsystems/image_031.png)</span>

    1.  Start in initial state s
    2.  Observe state of environment and create a perception
    3.  Update internal state based on perception
    4.  Select next action based on internal state
    5.  Execute selected action
    6.  Goto step 2

---
## Deliberative agents

*   Based on principles of symbolic artificial intelligence
*   Knowledge based systems with internal and symbolic representation of environment (”mental state”)
*   Decision-making as symbolic reasoning over mental state
*   Idea: Decision-making as theorem proving
*   Not detailed here …

---
## Reactive agents
*   Influenced by behaviourist psychology and biology
    *   Simple internal world representation
    *   Tight coupling of perception and action
    *   Simple decision rules
*   Simon (1981): The complexity of agent behaviour can be a reflection of the complexity of the agent’s environment rather than of the agent’s complex internal design
*   Often: communication via the environment (stigmergy)
*   Model: ant colonies, bee-hives (swarm intelligence)
*   Control architectures: e.g. subsumption architecture by Rodney Brooks

---
## Subsumption architecture (Brooks): Generate intelligent behaviour without explicit representation

<span>![image](slides/sociotechnicalsystems/image_032.png)</span>
    
---
## Control: suppression and inhibition

<img alt="" height="250" src="image_035.png" width="588">

*   S: signal from module (layer k) suppresses (and replaces) input into module (layer k-1) for some pre-determined time
*   I: signal from layer k inhibits output from module (at layer k-1) for some pre-determined time
*   This control is hard-wired

---
## Example: Ant-based routing (Dorigo et al., 2000)
*   Ants = simple agents that can traverse a graph
*   Ants look for food
*   Ants leave pheromone on the edges they traverse
*   Decision algorithm: Choose the edge with the highest pheromone concentration
<span>![image](slides/sociotechnicalsystems/image_038.jpg)</span>  
*   Ant-based algorithms have been successfully used to solve routing and scheduling problems
    
---
## Practical reasoning
*   Fundamental limitations to the design of deliberative agents
    *   Transforming subsymbolic data in symbolic form is difficult
    *   Decision algorithms undecidable for first-order language
    *   Worst case co-NP-complete for propositional logic
*   Alternative approach: Practical reasoning
    *   ”Practical reasoning is a matter of weighing conflicting ”considerations for and against competing options, where the relevant considerations are provided by what the agent <span style=" color: #808089;">desires/values/cares about</span> and what the agent <span style=" color: #808089;">believes”</span> (Bratman, 1990)
*   Combines
    *   *Deliberation*: What goals to achieve?
    * *Means-Ends Reasoning*: How to achieve goals?
*   Result of deliberation: *Intentions*

---
## Agents as Belief, Desire, Intention (BDI) systems
*   Belief:
    *   information about the current world state
    *   subjective
*   Desire:
    *   preferences over future world states
    *   can be inconsistent (in contrast to goals)
*   Intentions:
    *   set of goals the agent is committed to achieve
    *   the agent’s ”runtime stack”
*   Formal models:
    *   mostly modal logics with possible-worlds semantics
    *   more pragmatic implementations
Underlying theory: Daniel Dennett’s model of intentionality (Dennett, 1987)

---
## Intentionality: What is an agent?
*   Why would you want to ascribe human-like properties such as beliefs, goals, or intentions to a technical system? (McCarthy)

You always can!
*   ’It is perfectly coherent to treat a light switch as a (very cooperative) agent with the capability of transmitting current at will, who invariably transmits current when it believes that we want it transmitted and not otherwise; flicking the switch is simply our way of communicating our desires’. (Yoav Shoham, 1993)

Yet - Why do we find this absurd?

---
## It’s all about being useful
*   ... because it is not useful for us: we understand the underlying mechanism sufficiently to have a simpler explanation of system behaviour
*   The more we know about a system, the less intentional concepts can help us in describing it
*   The more complex computer systems get, the more we have to rely on abstractions and metaphors to explain their behaviour
*   Considering/modelling a system as an intentional system is such as abstraction and hence a tool to explaining, understanding and (ultimately) programming complex computer systems

---
## Means-Ends Reasoning
*   Means-Ends: ”from means to ends”
    *   Means = ”What can the agent do”
    *   Ends = Goals
*   Agent has:
    *   Representation of task to be achieved (intention)
    *   Representation of actions it can perform
    *   Representation of environment
*   Based on this knowledge it attempts to generate/select a plan to achieve the task/satisfy the intention
*   Planning = automated programming: the design of a course of action that will achieve some desired goal
*   Mechanisms for plan generation/selection
    *   Planning from first principles: Automated (AI) planning
    *   Planning from second principles: Select a suitable plan from library

---
## The mother of BDI architectures: IRMA (Bratman et al, 1987)

<span>![image](slides/sociotechnicalsystems/image_039.gif)</span>

---
## Abstract BDI Agent interpreter (Rao and Georgeff, 1991)

	initialize-state();
    repeat
        options = option-generator(event-queue); 
        selected-options = deliberate(options); 
        update-intentions(selected-options); execute();
        get-new-external-events(); 
        drop-successful-attitudes(); 
        drop-impossible-attitudes();
    end repeat
        
---
## Plattforms for modelling/running BDI agents
*   To realize systems as BDI agent systems, we need

    *   Programming/modelling languages
    *   Runtime execution platforms
    *   Modelling and development tools
*   First practically usable implemented BDI system: Procedural Reasoning System PRS (Georgeff and Lansky, 1987)
*   Since then: numerous extensions and re-implementations
*   In this course, we shall investigate as an example:
    *   Programming/modelling language: AgentSpeak
    *   Interpreter/Platform: LightJason

---
## PRS System (Agent) Architecture
<img alt="" height="500" src="image_040.png" width="752">


---
## AgentSpeak and LightJason
*   AgentSpeak is a language for defining executable PRS-style BDI agents
*   Paradigm: Logic Programming
    *   Facts are represented by logical formulae
    *   Solve problem/achieve goal -> Prove formulae via logical inference
*   LightJason is a scalable implementation of an AgentSpeak interpreter with many useful features and extensions
*   In the simulation lab part of this course, you will get an introduction into LightJason and have a chance to play around with the system by analysing and extending a small traffic scenario model

---
## Intelligent agents and multi-agent systems: Literature
<sup> **M. Bratman, (1990).** What is intention? In P. Cohen, J. Morgan and M. Pollack, editors, Intentions in Communication, pages 15–32\. The MIT Press: Cambridge, MA.
**M. Bratman, D.J. Israel, and M.E.Pollack (1987).** Toward an architecture for resource-bounded agents. Technical report CSLI-87-104, Stanford University.
**R. A. Brooks (1986).** A robust layered control system for a mobile robot. _IEEE Journal of Robotics and Automation_, 2(1):14–23.
**D. C. Dennett (1987).** _The Intentional Stance_. The MIT Press: Cambridge, MA.
**M. Dorigo, E. Bonabeau, G. Theraulaz (2000).** Ant algorithms and stigmergy. Future Generation Computer Systems 16 (2000) 851–871\. Elsevier.
**M.P. Georgeff, A.L.Lansky (1987).** Reactive reasoning and planning. In Proceedings of the Sixth National Conference on Artificial Intelligence (AAAI-87), pages 677-682, Seattle, WA.</sup>

---
## Intelligent agents and multi-agent systems: Literature
<sup> **D. Helbing, P. Molnár, P. (1995)**: Social force model for pedestrian dynamics; in: Physical Review E, 51\. Jg., S. 4282 - 4286.
**N.R.Jennings, K. Sycara, and M.J.Wooldridge (1998).** A Roadmap of Agent Research and Development In JAAMAS 1(1), pages 7-36\. July 1998.
**J. P. Müller (1996).** The Design of Autonomous Agents: A Layered Approach, volume 1177 of Lecture Notes in Artificial Intelligence. Springer-Verlag, Heidelberg, 1996.
**K. Nagel and M. Schreckenberg (1992).** _A cellular automaton model for freeway traffic_, J. Physique I 2, 2221-2229.
**A.S. Rao and M. Georgeff (1991).** Modelling agents within a BDI architecture. Proc. Of the 2nd Intl. Conf. On Principles of Knowledge Representation and Reasoning (KR’91), 1991.
**D. Sanderson, D. Busquets, and J. Pitt (2012).** A micro-meso-macro approach to intelligent transportation systems. In IEEE Sixth International Conference on Self-Adaptive and Self-Organizing Systems Workshops (SASOW), pages 71–76\. IEEE. 
</sup>

---
## Intelligent agents and multi-agent systems: Literature
<sup>**Y. Shoham (1993).** Agent-oriented programming. Artificial Intelligence, 60:51-92, 1993
**H. A. Simon (1981).** The Sciences of the Artiﬁcial (second edition). The MIT Press: Cambridge, MA.
**M.P. Singh (2014).** Norms as a basis for governing sociotechnical systems. ACM Trans. Intell. Syst. Technol., 5(1):21:1–21:23.
**R. G. Smith, R. Davis (1981).** Frameworks for Cooperation in Distributed Problem Solving. IEEE Transactions on Systems, Man, and Cybernetics, pp. 61-70, 11:1981 
**G. Vizzari, L. Manenti, K. Ohtsuka, K. Shimura (2015).** An Agent-Based Pedestrian and Group Dynamics Model Applied to Experimental and Real-World Scenarios. Journal of Intelligent Transportation Systems, 19(1):32-45,
**M. J. Wooldridge (2009).** An introduction to Multi-agent Systems. John Wiley & Sons, 2nd edition 2009. </sup>
