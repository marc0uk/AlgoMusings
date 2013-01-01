package org.tullio.algo.dp;

public class KspSolution {

	private final int optimalValue;
	private final int usedItems;
	private final boolean[] flag;
	
	public KspSolution(final int solution, final boolean[] usedFlag) {
		optimalValue = solution;
		flag = usedFlag;
		int used = 0;
		for (boolean one : flag) {
			if (one) {
				used++;
			}
		}
		usedItems = used;
	}

	public KspSolution(final int solution, final int numItemUsed, final boolean[] usedFlag) {
		optimalValue = solution;
		usedItems = numItemUsed;
		flag = usedFlag;
	}
	
	public int optimalValue() {
		return optimalValue;
	}
	
	public int usedItems() {
		return usedItems;
	}
	
	public boolean[] flag() {
		return flag;
	}
	
	public void printSubSet() {
		for (int i=0; i<flag.length; i++) {
			System.out.println(String.format("item[%d] -> %s", 
					i, 
					(flag[i] ? "PICKED" : "-")));
		}
	}
}
