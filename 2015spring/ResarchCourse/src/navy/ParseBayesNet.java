package navy;

import embayes.data.BayesNet;
import embayes.data.CategoricalProbability;
import embayes.data.CategoricalVariable;
import gen.Example1;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParseBayesNet {

	public static void main(String[] args) throws IOException {

		Example1 ex1 = new Example1();
		BayesNet network = ex1.getNetwork();
		// get random number for nodes
		// how many records
		int recordsNum = 1000;
		int nodesNum = network.numberVariables();
		double[][] randomNums = new double[nodesNum][];
		for (int i = 0; i < randomNums.length; i++) {
			randomNums[i] = Utility.random(recordsNum);
		}
//		System.out.println("====================");
		List<CategoricalVariable> roots = new ArrayList<CategoricalVariable>();
		CategoricalVariable[] nodes = network.getVariables();

		for (int i = 0; i < nodes.length; i++) {
			// find all root nodes and compute them first
			if (network.getParentsAndSelf(nodes[i]).length == 1) {
//				System.out
//						.println(nodes[i].getIndex()
//								+ ", "
//								+ Arrays.toString(nodes[i].getProbability()
//										.getValues()));
				roots.add(nodes[i]);
			}
		}
		
		FileWriter writer = new FileWriter("Example.csv");
		for (int i = 0; i < recordsNum; i++) {
			Map<String, String> nodeVars = new HashMap<String, String>();
			List<CategoricalVariable> computeQueue = new ArrayList<CategoricalVariable>();
			computeQueue.addAll(roots);
			while (computeQueue.size() > 0) {
				handleQueue(i, randomNums, computeQueue, nodeVars, network);
			}
			//System.out.println(nodeVars.values());
			String record = nodeVars.get("n0");
			for (int j = 1; j < nodes.length; j++) {
				record += "," + nodeVars.get("n" + j);
			}
			writer.write(record + "\n");
			System.out.println(record);

		}
		writer.close();

	}

	private static void handleQueue(int col, double[][] values,
			List<CategoricalVariable> computeQueue,
			Map<String, String> nodeVars, BayesNet network) {
		List<CategoricalVariable> tmpQueue = new ArrayList<CategoricalVariable>();
		for (CategoricalVariable var : computeQueue) {
			CategoricalVariable[] parents = network.getParentsAndSelf(var);
			if (parents.length == 1) {
				// root node
				CategoricalVariable[] children = network.getChildren(var);
				for (int i = 0; i < children.length; i++) {
					tmpQueue.add(children[i]);
				}
//				System.out.println("node name:" + var.getName());
//				System.out.println("index=" + var.getIndex());
//				System.out.println("col=" + col);
//				System.out.println("values length: " + values.length
//						+ ", values subset length: "
//						+ values[var.getIndex()].length);
				// handle value
				double value = values[var.getIndex()][col];
				CategoricalProbability prob = var.getProbability();
				if (value <= prob.getValue(0)) {
					// False
					nodeVars.put(var.getName(), "0");
				} else {
					// True
					nodeVars.put(var.getName(), "1");
				}

			} else {
				// has parent
				boolean wait = false;
				String parentNodeVars = "";
				for (int i = 0; i < parents.length; i++) {
					if (parents[i].equals(var))
						continue;
					String nodeVar = nodeVars.get(parents[i].getName());
					if (nodeVar == null) {
						// need to wait parent's value
						tmpQueue.add(var);
						wait = true;
						break;
					} else {
						parentNodeVars += nodeVar;
					}
				}
				if (wait)
					continue;
				// handle value
				double value = values[var.getIndex()][col];
				CategoricalProbability prob = var.getProbability();
//				System.out.println("binary string: " + parentNodeVars);
//				System.out.println("binary number: "
//						+ Integer.parseInt(parentNodeVars, 2));
//				System.out.println(Arrays.toString(var.getProbability().getValues()));
//				System.out.println(prob.getValue(Integer.parseInt(parentNodeVars, 2)));
				if (value <= prob.getValue(Integer.parseInt(parentNodeVars, 2))) {
					// False
					nodeVars.put(var.getName(), "0");
				} else {
					// True
					nodeVars.put(var.getName(), "1");
				}
				CategoricalVariable[] children = network.getChildren(var);
				for (int i = 0; i < children.length; i++) {
					tmpQueue.add(children[i]);
				}
			}
		}
		computeQueue.clear();
		computeQueue.addAll(tmpQueue);
		tmpQueue.clear();

	}

}
