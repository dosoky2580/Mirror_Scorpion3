from kivymd.app import MDApp
from kivymd.uix.screen import MDScreen
from kivymd.uix.label import MDLabel

class MirrorApp(MDApp):
    def build(self):
        return MDLabel(text="Mirror Pro - Welcome Tamer", halign="center")

if __name__ == "__main__":
    MirrorApp().run()
