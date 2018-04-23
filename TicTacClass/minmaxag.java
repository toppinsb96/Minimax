/*
    Brandon Toppins

    Minimax Tic-Tac-Toe
*/
import java.util.*;
import java.lang.*;

public class minmaxag
{

    String S;
    String x = "X"; // Human
    String o = "O"; // AI
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
    //==============================================================================
    //                      Utility Functions
    //==============================================================================
    public ArrayList<Integer> getActions(String[] state)
    {
        ArrayList<Integer> actions = new ArrayList<Integer>();

        for(int i = 0; i < 9; i++)
        {
            if(state[i].equals("_"))
            {
                actions.add(i);
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
            String[] suc = new String[9];
            System.arraycopy(state, 0, suc,  0, state.length);

            suc[i] = ""+p;
            successors.add(suc);

        }
        return successors;
    }


    public Boolean terminal(String p, String[] state)
    {
        if ((state[0].equals(p) && state[1].equals(p) && state[2].equals(p)) ||
            (state[3].equals(p) && state[4].equals(p) && state[5].equals(p)) ||
            (state[6].equals(p) && state[7].equals(p) && state[8].equals(p)) ||
            (state[0].equals(p) && state[3].equals(p) && state[6].equals(p)) ||
            (state[0].equals(p) && state[4].equals(p) && state[8].equals(p)) ||
            (state[2].equals(p) && state[4].equals(p) && state[6].equals(p)) ||
            (state[1].equals(p) && state[4].equals(p) && state[7].equals(p)) ||
            (state[2].equals(p) && state[5].equals(p) && state[8].equals(p)))
            {
                return true;
            }
            else
            {
                return false;
            }
    }
    //==============================================================================
    //                      Minimax Functions
    //==============================================================================
    public int maxValue(String[] state)
    {
        ArrayList<Integer> actions = getActions(state);
        ArrayList<String[]> successors = getSuccessors(state, o);

        if(terminal(o, state))
        {
            return 10;
        }

        if(actions.size() < 1)
        {
            return 0;
        }
        int v = -1000;
        for(String[] s : successors)
        {
            v = findMax(v, minValue(s));
        }
        return v;
    }

    public int minValue(String[] state)
    {
        ArrayList<Integer> actions = getActions(state);
        ArrayList<String[]> successors = getSuccessors(state, x);


        if(terminal(x, state))
        {
            return -10;
        }
        if(actions.size() < 1)
        {
            return 0;
        }
        int v = 1000;

        for(String[] s : successors)
        {
            v = findMin(v, maxValue(s));
        }
        return v;
    }
    public int findAction(String[] begin, String[] end)
    {
        for(int i = 0; i<9; i++)
        {
            if(begin[i] != end[i])
                return i;
        }
        return -1;
    }
    // minimax decision
    public int move(String S)
    {
        this.S = S;
        String[] state = S.split("");
        ArrayList<Integer> actions = getActions(state);
        ArrayList<String[]> successors = getSuccessors(state, o);

        int m = -1000;
        int best = -1000;
        String[] bestMove = new String[9];

        for(String[] s : successors)
        {
            m = findMax(m, minValue(s));
            if(m > best)
            {
                best = m;
                bestMove = s;
            }
        }
        return findAction(state, bestMove);
    }
}
