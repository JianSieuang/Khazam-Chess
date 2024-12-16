import javax.swing.*;
import java.awt.*;

public class ButtonDesign {

    public static JButton createMenuButton(String text, Color backgroundColor, JFrame frame, JPanel panel, Runnable onClickAction) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(backgroundColor);
        button.setFont(new Font("Arial", Font.BOLD, 17));

        // 动态调整按钮大小：保持 panel 宽度的 40%，高度固定为 50
        adjustButtonSize(button, panel);

        // 设置按钮的鼠标事件
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(Color.GRAY);
                button.setForeground(Color.WHITE);
                new BtnSound("hover").actionPerformed(null); // 播放 hover 音效
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(backgroundColor);
                button.setForeground(Color.BLACK);
            }
        });

        // 当按钮被点击时
        button.addActionListener(e -> {
            new BtnSound("click").actionPerformed(null); // 播放点击音效
            onClickAction.run();
        });

        // 添加对 panel 大小变化的监听器
        panel.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                adjustButtonSize(button, panel);
                button.revalidate();
                button.repaint();
            }
        });

        return button;
    }

    // 调整按钮大小的方法
    private static void adjustButtonSize(JButton button, JPanel panel) {
        int panelWidth = panel.getWidth();
        int buttonWidth = (int) (panelWidth * 0.4); // 按钮宽度为 panel 宽度的 40%
        int buttonHeight = 50; // 固定高度
        button.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        button.setMaximumSize(new Dimension(buttonWidth, buttonHeight));
    }
}
