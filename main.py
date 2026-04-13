from kivy.app import App
from kivy.uix.label import Label

class MirrorApp(App):
    def build(self):
        return Label(text="Hello Tamer - Mirror is Alive")

if __name__ == "__main__":
    MirrorApp().run()
