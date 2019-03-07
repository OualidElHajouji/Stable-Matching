public class StableMatching implements StableMatchingInterface {
	
	public int[][] constructStableMatching(int[] menGroupCount,
    int[] womenGroupCount,
    int[][] menPrefs,
    int[][] womenPrefs
  ){
		// returns a stable matching in the form of a n*n matrix
		
		int m = menGroupCount.length;
		int w = womenGroupCount.length;
		int[][] M = new int[m][w]; /* Final result */
		int n = java.util.stream.IntStream.of(menGroupCount).sum(); /* sum of the elts of menGroupCount (total nb of men) */
       		int engagedCount = 0; /* nb of couples */
        	int i; /* indicates the current gp of men */
        	int nextGpOfMen = -1; /* helps choose the next gp of men considered (avoids always using the function chooseMenGroup) */
        	int[] menUnengagedCount  = new int[m]; /* counts the nb of unengaged men in each gp */
		int[] womenUnengagedCount = new int[w]; /* same for women */
		
		int[] nextWomenGroup = new int[m]; /*tab of the ranking of the women group chosen by each group of men : intially, all equal to 0 */
		
		int[] leastAttractiveMen = new int[w]; /* tab of the  least attractive men to which a woman of a group j is married */
		
		int[][] getRank = new int[w][m]; /* getRank[j][i] is the rank of group i of men in the list of preference of the group j of women */
		
		/*Initialisation of the 4 tabs (the 2 others are initialised to 0) */
		for (int k = 0; k< m ; k++) {
			menUnengagedCount[k] = menGroupCount[k];
		}
		for (int j = 0; j< w ; j++) {
			womenUnengagedCount[j] = womenGroupCount[j];
			leastAttractiveMen[j]= -1; /* there's no couple at the beginning */
			for(int k = 0; k<m; k++) {
				getRank[j][womenPrefs[j][k]] = k;
			}
		}
		
		/* main loop */
		while(engagedCount < n) {
		
			/* For time complexity reasons, we use the function chooseMenGroup only when nextGpOfMen = -1 */
			if (nextGpOfMen == -1)  {i = chooseMenGroup(m, n - engagedCount, menUnengagedCount);}
			else { i = nextGpOfMen;}
			
			int k = nextWomenGroup[i];
			
			int j = menPrefs[i][k]; /* first group of women chosen */
				
			
			if (womenUnengagedCount[j]>0) {  /* if there are single people, we form as many couples as possible */
					
					
					int nbOfCouples1 = Math.min(menUnengagedCount[i], womenUnengagedCount[j]);
					menUnengagedCount[i] = menUnengagedCount[i] - nbOfCouples1;
					womenUnengagedCount[j] = womenUnengagedCount[j] - nbOfCouples1;
					M[i][j] = M[i][j] + nbOfCouples1;
					engagedCount += nbOfCouples1;
					
					nextGpOfMen = -1;
					
					if(leastAttractiveMen[j] == -1 || getRank[j][i]> getRank[j][leastAttractiveMen[j]]) {
						leastAttractiveMen[j]=i;
					}
				}
			
			else { /* We have to find whether women from group j are married to less attractive (to them) men than the ones from group i */
					
					int iPrime = womenPrefs[j][m-1];
					if (leastAttractiveMen[j] != -1) iPrime = leastAttractiveMen[j];
					
					if (getRank[j][iPrime]> getRank[j][i]) { /* if the least attractive is less attractive than i, we form new couples */
						
						int nbOfCouples2 = Math.min(M[iPrime][j], menUnengagedCount[i]);
						menUnengagedCount[i] = menUnengagedCount[i] - nbOfCouples2;
						menUnengagedCount[iPrime] = menUnengagedCount[iPrime] + nbOfCouples2;
						M[i][j] = M[i][j] + nbOfCouples2;
						M[iPrime][j] = M[iPrime][j] - nbOfCouples2;
						
						if(menUnengagedCount[i]==0) nextGpOfMen = iPrime;
						else {nextGpOfMen = -1; }
						if (M[iPrime][j]==0) {
						for(int l = getRank[j][iPrime]-1; l>= 0; l--) { /* we find the next least attractive man among the ones who have better ranking than iPrime : someone will be found as i got married to j */
							if (M[womenPrefs[j][l]][j]>0) {
								leastAttractiveMen[j]= womenPrefs[j][l];
								break;
							}
						}
						}
						}
					else { /* We will keep the same gp of men and change the gp of women */
						
				    	nextWomenGroup[i] += 1;
					nextGpOfMen = i;
					}
			
		}
		}
		return M;
		}
	
	/* enables to choose the current group i of men from which the next engaged man will be chosen : here we use the criteria of the 1/2m fraction given in the instructions, 
	 but we could also choose the group with the highest nb of unengaged men */
	public int chooseMenGroup(int m, int totalUnengaged, int[] menUnengagedCount) {
		double nbMin = ((double) totalUnengaged)/(2*m);
		for (int i = 0; i<m; i++) {
			if (menUnengagedCount[i]>= nbMin) return i;
		}
		return -1; /* we already know that a group with the correct nb of unengaged men will be found */
	}
	
	
	
	
	
} 





