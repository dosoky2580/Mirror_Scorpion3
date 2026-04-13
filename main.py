from kivymd.app import MDApp
from kivymd.uix.screen import MDScreen
from kivymd.uix.button import MDRaisedButton
from kivymd.uix.label import MDLabel
from kivy.lang import Builder

class MirrorApp(MDApp):
    def build(self):
        self.theme_cls.primary_palette = "Blue"
        screen = MDScreen()
        
        # رسالة ترحيب
        screen.add_widget(
            MDLabel(
                text="Welcome to Mirror Pro\nTesting Mode",
                halign="center",
                font_style="H4",
                pos_hint={"center_y": 0.7}
            )
        )
        
        # زرار تجريبي
        screen.add_widget(
            MDRaisedButton(
                text="Start Scanner",
                pos_hint={"center_x": 0.5, "center_y": 0.4},
                on_release=self.test_button
            )
        )
        return screen

    def test_button(self, *args):
        print("Button Clicked - Testing Camera Permissions")

if __name__ == "__main__":
    MirrorApp().run()
