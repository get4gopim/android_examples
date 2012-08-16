package com.example.moviedb;

import android.widget.CheckBox;
import android.widget.TextView;

public class PlanetViewHolder {

    private CheckBox checkBox ;
    private TextView textView ;
    private TextView textGenere ;
    private long id ;
    
    public PlanetViewHolder() {}
    
    public PlanetViewHolder( TextView textView, TextView textGenere, CheckBox checkBox ) {
      this.checkBox = checkBox ;
      this.textGenere = textGenere;
      this.textView = textView ;
    }
    public CheckBox getCheckBox() {
      return checkBox;
    }
    public void setCheckBox(CheckBox checkBox) {
      this.checkBox = checkBox;
    }
    public TextView getTextView() {
      return textView;
    }
    public void setTextView(TextView textView) {
      this.textView = textView;
    }

	public TextView getTextGenere() {
		return textGenere;
	}

	public void setTextGenere(TextView textGenere) {
		this.textGenere = textGenere;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	} 
    
    
}
