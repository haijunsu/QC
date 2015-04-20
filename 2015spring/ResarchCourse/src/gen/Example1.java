package gen;
/*
 * example1.java
 * Number of nodes:20
 * This network was created on Thu Feb 26 23:47:28 EST 2015

 * @author : BNGenerator (Random and uniform DAG generator)
 *            and Jaime Ide (for function generation)
 */
import embayes.data.*;

public class Example1 {

  private BayesNet network;
  public BayesNet getNetwork() { return(network); }

  public Example1() {

	DataFactory factory =
			 embayes.data.impl.DataBasicFactory.getInstance();
	network = factory.newBayesNet();
	network.setName("example1");

	CategoricalVariable n0 =
		factory.newCategoricalVariable("n0", new String[] {"state0","state1"});
	CategoricalVariable n1 =
		factory.newCategoricalVariable("n1", new String[] {"state0","state1"});
	CategoricalVariable n2 =
		factory.newCategoricalVariable("n2", new String[] {"state0","state1"});
	CategoricalVariable n3 =
		factory.newCategoricalVariable("n3", new String[] {"state0","state1"});
	CategoricalVariable n4 =
		factory.newCategoricalVariable("n4", new String[] {"state0","state1"});
	CategoricalVariable n5 =
		factory.newCategoricalVariable("n5", new String[] {"state0","state1"});
	CategoricalVariable n6 =
		factory.newCategoricalVariable("n6", new String[] {"state0","state1"});
	CategoricalVariable n7 =
		factory.newCategoricalVariable("n7", new String[] {"state0","state1"});
	CategoricalVariable n8 =
		factory.newCategoricalVariable("n8", new String[] {"state0","state1"});
	CategoricalVariable n9 =
		factory.newCategoricalVariable("n9", new String[] {"state0","state1"});
	CategoricalVariable n10 =
		factory.newCategoricalVariable("n10", new String[] {"state0","state1"});
	CategoricalVariable n11 =
		factory.newCategoricalVariable("n11", new String[] {"state0","state1"});
	CategoricalVariable n12 =
		factory.newCategoricalVariable("n12", new String[] {"state0","state1"});
	CategoricalVariable n13 =
		factory.newCategoricalVariable("n13", new String[] {"state0","state1"});
	CategoricalVariable n14 =
		factory.newCategoricalVariable("n14", new String[] {"state0","state1"});
	CategoricalVariable n15 =
		factory.newCategoricalVariable("n15", new String[] {"state0","state1"});
	CategoricalVariable n16 =
		factory.newCategoricalVariable("n16", new String[] {"state0","state1"});
	CategoricalVariable n17 =
		factory.newCategoricalVariable("n17", new String[] {"state0","state1"});
	CategoricalVariable n18 =
		factory.newCategoricalVariable("n18", new String[] {"state0","state1"});
	CategoricalVariable n19 =
		factory.newCategoricalVariable("n19", new String[] {"state0","state1"});
	CategoricalProbability p0 =
		factory.newCategoricalProbability(n0,
		new double[] {0.1155837 ,0.8844163 });
	CategoricalProbability p1 =
		factory.newCategoricalProbability(n1,
		new CategoricalVariable[] {n8},
		new double[] {0.0028286772 ,0.5508742 ,0.9971713 ,0.44912583 });
	CategoricalProbability p2 =
		factory.newCategoricalProbability(n2,
		new CategoricalVariable[] {n7,n12,n14},
		new double[] {0.3601403 ,0.31074995 ,0.2885504 ,0.43103057 ,0.62967694 ,0.93610305 ,0.69132066 ,0.48453623 ,0.6398597 ,0.68925005 ,0.71144956 ,0.56896937 ,0.37032312 ,0.063896894 ,0.30867937 ,0.51546377 });
	CategoricalProbability p3 =
		factory.newCategoricalProbability(n3,
		new CategoricalVariable[] {n13,n6},
		new double[] {0.6377355 ,0.7272106 ,0.20951624 ,0.73454064 ,0.36226445 ,0.27278942 ,0.7904838 ,0.2654594 });
	CategoricalProbability p4 =
		factory.newCategoricalProbability(n4,
		new CategoricalVariable[] {n15,n6,n0},
		new double[] {0.24044156 ,0.8917488 ,0.80083525 ,0.71211046 ,0.23079279 ,0.42722768 ,0.035419464 ,0.2522618 ,0.75955844 ,0.1082512 ,0.19916476 ,0.28788957 ,0.7692072 ,0.57277226 ,0.96458054 ,0.74773824 });
	CategoricalProbability p5 =
		factory.newCategoricalProbability(n5,
		new CategoricalVariable[] {n8,n0},
		new double[] {0.015052781 ,0.26844108 ,0.545614 ,0.50564116 ,0.9849472 ,0.731559 ,0.45438597 ,0.49435884 });
	CategoricalProbability p6 =
		factory.newCategoricalProbability(n6,
		new double[] {0.24562517 ,0.7543748 });
	CategoricalProbability p7 =
		factory.newCategoricalProbability(n7,
		new CategoricalVariable[] {n5},
		new double[] {0.047942102 ,0.5548376 ,0.95205796 ,0.44516242 });
	CategoricalProbability p8 =
		factory.newCategoricalProbability(n8,
		new CategoricalVariable[] {n6},
		new double[] {0.286544 ,0.9833244 ,0.71345603 ,0.016675636 });
	CategoricalProbability p9 =
		factory.newCategoricalProbability(n9,
		new CategoricalVariable[] {n3},
		new double[] {0.5005508 ,0.20704207 ,0.49944913 ,0.79295796 });
	CategoricalProbability p10 =
		factory.newCategoricalProbability(n10,
		new CategoricalVariable[] {n7},
		new double[] {0.5982662 ,0.2779224 ,0.40173376 ,0.7220776 });
	CategoricalProbability p11 =
		factory.newCategoricalProbability(n11,
		new CategoricalVariable[] {n0,n18},
		new double[] {0.8389733 ,0.10816581 ,0.7569648 ,0.52993345 ,0.16102676 ,0.8918342 ,0.2430352 ,0.4700665 });
	CategoricalProbability p12 =
		factory.newCategoricalProbability(n12,
		new double[] {0.19636159 ,0.8036384 });
	CategoricalProbability p13 =
		factory.newCategoricalProbability(n13,
		new CategoricalVariable[] {n10},
		new double[] {0.15333708 ,0.22402517 ,0.84666294 ,0.77597487 });
	CategoricalProbability p14 =
		factory.newCategoricalProbability(n14,
		new CategoricalVariable[] {n18},
		new double[] {0.5779694 ,0.10659869 ,0.42203057 ,0.89340127 });
	CategoricalProbability p15 =
		factory.newCategoricalProbability(n15,
		new CategoricalVariable[] {n9,n12},
		new double[] {0.34400037 ,0.30699587 ,0.5802003 ,0.6266585 ,0.6559996 ,0.6930042 ,0.4197996 ,0.37334153 });
	CategoricalProbability p16 =
		factory.newCategoricalProbability(n16,
		new double[] {0.50674486 ,0.4932552 });
	CategoricalProbability p17 =
		factory.newCategoricalProbability(n17,
		new CategoricalVariable[] {n9,n1,n13},
		new double[] {0.21712892 ,0.88060015 ,0.59127426 ,0.91474575 ,0.6691758 ,0.33413607 ,0.20671146 ,0.23338774 ,0.7828711 ,0.11939987 ,0.40872574 ,0.08525421 ,0.33082426 ,0.665864 ,0.7932885 ,0.76661223 });
	CategoricalProbability p18 =
		factory.newCategoricalProbability(n18,
		new double[] {0.7546974 ,0.24530268 });
	CategoricalProbability p19 =
		factory.newCategoricalProbability(n19,
		new CategoricalVariable[] {n11,n16},
		new double[] {0.4846789 ,0.32051316 ,0.28460503 ,0.6340995 ,0.5153212 ,0.6794869 ,0.715395 ,0.36590055 });
network.setVariables( new CategoricalVariable[] {
n0,n1,n2,n3,n4,n5,n6,n7,n8,n9,n10,n11,n12,n13,n14,n15,n16,n17,n18,n19});
network.setProbabilities( new CategoricalProbability[] {
p0,p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12,p13,p14,p15,p16,p17,p18,p19});
  } // end of public
}  // end of class
