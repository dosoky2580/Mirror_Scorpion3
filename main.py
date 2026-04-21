from kivymd.app import MDApp
from kivy.lang import Builder
from kivy.uix.screenmanager import ScreenManager, Screen

try:
    from documents_logic import DocumentsScreen
    from inspiration_logic import InspirationScreen
    from dialogue_logic import DialogueScreen
    from text_translation_logic import TextTranslationScreen
    from games_logic import GamesScreen
    from settings_logic import SettingsScreen
except ImportError as e:
    print(f"Error: {e}")
    class DocumentsScreen(Screen): pass
    class InspirationScreen(Screen): pass
    class DialogueScreen(Screen): pass
    class TextTranslationScreen(Screen): pass
    class GamesScreen(Screen): pass
    class SettingsScreen(Screen): pass

KV = '''
ScreenManager:
    MainScreen:
    TextTranslationScreen:
        name: 'text_trans_screen'
    DialogueScreen:
        name: 'dialogue_screen'
    DocumentsScreen:
        name: 'docs_screen'
    InspirationScreen:
        name: 'inspiration_screen'
    GamesScreen:
        name: 'games_screen'
    SettingsScreen:
        name: 'settings_screen'

<MainScreen>:
    name: 'main'
    md_bg_color: [0.05, 0.05, 0.05, 1]
    
    MDBoxLayout:
        orientation: 'vertical'
        padding: "10dp"
        spacing: "10dp"
        
        MDBoxLayout:
            size_hint_y: 0.25
            canvas.before:
                Color:
                    rgba: 1, 1, 1, 1
                Rectangle:
                    source: 'scorpion_mirror.png'
                    pos: self.pos
                    size: self.size

        MDScrollView:
            MDGridLayout:
                cols: 1
                adaptive_height: True
                spacing: "10dp"
                padding: "10dp"

                # الـ 6 كروت كاملة
                MDRaisedButton:
                    text: "1. الترجمة النصية"
                    size_hint: 1, None
                    height: "60dp"
                    on_release: root.manager.current = 'text_trans_screen'

                MDRaisedButton:
                    text: "2. حوار مترجم"
                    size_hint: 1, None
                    height: "60dp"
                    on_release: root.manager.current = 'dialogue_screen'

                MDRaisedButton:
                    text: "3. ترجمة مستندات وعدسة"
                    size_hint: 1, None
                    height: "60dp"
                    on_release: root.manager.current = 'docs_screen'

                MDRaisedButton:
                    text: "4. أحاديث وقصص وإلهام"
                    size_hint: 1, None
                    height: "60dp"
                    md_bg_color: [0.2, 0.1, 0.3, 1]
                    on_release: root.manager.current = 'inspiration_screen'

                MDRaisedButton:
                    text: "5. الألعاب"
                    size_hint: 1, None
                    height: "60dp"
                    on_release: root.manager.current = 'games_screen'

                MDRaisedButton:
                    text: "6. الإعدادات"
                    size_hint: 1, None
                    height: "60dp"
                    md_bg_color: [0.2, 0.2, 0.2, 1]
                    on_release: root.manager.current = 'settings_screen'
'''

class MainScreen(Screen):
    pass

class MirrorScorpionApp(MDApp):
    def build(self):
        self.theme_cls.theme_style = "Dark"
        self.theme_cls.primary_palette = "Red"
        return Builder.load_string(KV)

if __name__ == "__main__":
    MirrorScorpionApp().run()
