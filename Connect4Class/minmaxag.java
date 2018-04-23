/*
    Brandon Toppins

    Minimax Connect4
*/
import java.util.*;

public class minmaxag {


    String S;
    String x = "X"; // Human
    String o = "O"; // AI
    String e = "_"; // Empty

    int limit = 4;

    public minmaxag()
    {
        this.S = "";
    }
    public int findMax(int a, int b)
    {
        if(a > b)
            return a;
        return b;
    }
    public int findMin(int a, int b)
    {
        if(a < b)
            return a;
        return b;
    }
    public int findAction(String[] begin, String[] end)
    {
        for(int i = 0; i<begin.length; i++)
        {
            if(begin[i] != end[i])
                return i;
        }
        return -1;
    }
    //==============================================================================
    //                      Utility Functions
    //==============================================================================
    public int connect(String p1, String p2, String p3, String p4, String[] state)
    {
        int amt = 0;
        // Check rows
        for(int i = 35; i >= 0; i -= 7)
        {
            for(int j = 0; j < 4; j++)
            {
                int k = i + j;
                if(state[k].equals(p1) && state[k+1].equals(p2) && state[k+2].equals(p3) && state[k+3].equals(p4))
                    amt++;
            }
        }
        // Check columns
        for(int i = 0; i < 7; i++)
        {
            for(int j = 0; j <= 14; j += 7)
            {
                int k = i + j;
                if(state[k+21].equals(p1) && state[k+14].equals(p2) && state[k+7].equals(p3) && state[k].equals(p4))
                    amt++;
            }
        }
        // Check Diagonial Up
        for(int i = 0; i < 4; i++)
        {
            for(int j = 0; j <= 14; j += 7)
            {
                int k = i + j;
                if(state[k+24].equals(p1) && state[k+16].equals(p2) && state[k+8].equals(p3) && state[k].equals(p4))
                    amt++;
            }
        }
        // Check Diagonial Down
        for(int i = 3; i <= 6; i++)
        {
            for(int j = 0; j <= 17; j += 7)
            {
                int k = i + j;
                if(state[k+18].equals(p1) && state[k+12].equals(p2) && state[k+6].equals(p3) && state[k].equals(p4))
                    amt++;
            }
        }

        return amt;
    }

    public int evalState(String[] state)
    {
        int amt = 0;
        int value = 0;

        // Possibly unnecessary because win state
        amt = connect(o,o,o,e,state);
        value +=  100 * amt;
        amt = connect(o,o,e,e,state);
        value +=  10 * amt;
        amt = connect(o,e,e,e,state);
        value +=  1 * amt;

        amt = connect(x,x,x,e,state);
        value -=  100 * amt;
        amt = connect(x,x,e,e,state);
        value -=  10 * amt;
        amt = connect(x,e,e,e,state);
        value -=  1 * amt;

        return value;
    }


    public ArrayList<Integer> getActions(String[] state)
    {
        ArrayList<Integer> actions = new ArrayList<Integer>();

        for(int i = 35; i < 42; i++)
        {
            for(int j = 0; j <= 35; j += 7)
            {
                int k = i - j;
                if(state[k].equals(e))
                {
                    actions.add(k);
                    break;
                }
            }
        }
        return actions;
    }
    public ArrayList<String[]> getSuccessors(String[] state, String p)
    {
        ArrayList<String[]> successors = new ArrayList<String[]>();
        ArrayList<Integer> actions = getActions(state);


        for(int i : actions)
        {
            String[] suc = new String[42];
            System.arraycopy(state, 0, suc,  0, state.length);

            suc[i] = ""+p;
            successors.add(suc);
        }
        return successors;
    }

    //==============================================================================
    //                      Minimax Functions
    //==============================================================================
    public int maxValue(String[] state, int d)
    {
        ArrayList<Integer> actions = getActions(state);
        ArrayList<String[]> successors = getSuccessors(state, x);
        if(d > limit)
        {
            return evalState(state);
        }
        if(connect(o,o,o,o,state) > 0)
        {
            return 1000;
        }
        if(connect(x,x,x,x,state) > 0)
        {
            return -1000;
        }

        d++;
        int v = -1000;
        for(String[] s : successors)
        {
            v = findMax(v, minValue(s,d));
        }
        return v;
    }

    public int minValue(String[] state, int d)
    {
        ArrayList<Integer> actions = getActions(state);
        ArrayList<String[]> successors = getSuccessors(state, x);
        if(d > limit)
        {
            return evalState(state);
        }
        if(connect(o,o,o,o,state) > 0)
        {
            return 1000;
        }
        if(connect(x,x,x,x,state) > 0)
        {
            return -1000;
        }

        d++;
        int v = 1000;
        for(String[] s : successors)
        {
            v = findMin(v, maxValue(s,d));
        }
        return v;
    }

    public int move(String S)
    {
        this.S = S;

        String[] state = S.split("");
        ArrayList<Integer> actions = getActions(state);
        ArrayList<String[]> successors = getSuccessors(state, o);

        int m = -1000;
        int best = -1000;
        String[] bestMove = new String[9];

        int depth = 0;
        for(String[] s : successors)
        {
            //printArray(s);
            m = findMax(m, minValue(s, depth));
            if(m > best)
            {
                best = m;
                bestMove = s;
            }
        }
        return findAction(state, bestMove);
    }
}



/*
amt = connect(p,p,p,p,state);
value += 1000 * amt;*/
