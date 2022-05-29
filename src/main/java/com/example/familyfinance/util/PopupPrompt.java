package com.example.familyfinance.util;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;

public class PopupPrompt {
    public void new_style(String message){
        Frame frame=new Frame();
        //将弹窗放在最前面
        frame.setAlwaysOnTop(true);

        // 设置按钮显示效果  new Font(String 字体，int 风格，int 字号);
        UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("宋体", Font.PLAIN, 20)));
        // 设置文本显示效果
        UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("宋体", Font.PLAIN, 20)));
        JOptionPane.showMessageDialog(frame,message, "提示", 1);
    }

}
