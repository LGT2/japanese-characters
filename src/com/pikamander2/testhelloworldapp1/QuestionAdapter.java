package com.pikamander2.testhelloworldapp1;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

public class QuestionAdapter extends BaseAdapter 
{
    private QuizActivity mContext;
    
    public ArrayList<String> choices = new ArrayList<String>();
    public ArrayList<Button> buttons = new ArrayList<Button>();
    
    public Random random = new Random();
    public static Question question;

    public QuestionAdapter(Context c, Question q) 
    {
        mContext = (QuizActivity)c;
        question = q;
    }
    
    public void changeAnswers()
    {
    	buttons.get(0).setText(question.getAnswer(0));
    	buttons.get(1).setText(question.getAnswer(1));
    	buttons.get(2).setText(question.getAnswer(2));
    	buttons.get(3).setText(question.getAnswer(3));
    }

    public void makeButtons()
    {
    	for (int i = 0; i < 4; i++)
    	{
            Button newButton = new Button(mContext);
            newButton.setOnClickListener(mContext.mCorkyListener);
            buttons.add(newButton);
            newButton.setText(question.getAnswer(i));
            newButton.setTextColor(Color.rgb(255,255,255));
    	}
    }
    
    public int getCount() 
    {
        return 4;
    }

    public Object getItem(int position) 
    {
        return null;
    }

    public long getItemId(int position) 
    {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) 
    {
    	if (buttons.size() == 0)
    	{
    		makeButtons();
    	}
    	
        return buttons.get(position);
    }
}