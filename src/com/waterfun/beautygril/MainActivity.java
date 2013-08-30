/****************************************************************************
 * Copyright (c) 2010-2012 cocos2d-x.org
 *
 * http://www.cocos2d-x.org
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 ****************************************************************************/
package com.waterfun.beautygril;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.annotation.view.ViewInject;

public class MainActivity extends FinalActivity
{

    @ViewInject(id = R.id.button, click = "btnClick")
    Button button;

    @ViewInject(id =R.id.imageView,click = "")
    ImageView imageView;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.main);


    }

    public void btnClick(View v)
    {
        try
        {
            FinalBitmap finalBitmap =FinalBitmap.create(this);
            finalBitmap.display(imageView,"http://appimg.b0.upaiyun.com/tbqingqu/1376802371097/0.JPG!480");
        }
        catch (Exception e)
        {
            System.out.print(e.getMessage());
        }
    }

}
