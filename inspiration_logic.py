import json
import random
from kivymd.uix.screen import MDScreen
from kivy.lang import Builder

KV_INSPIRATION = '''
<InspirationScreen>:
    md_bg_color: [0.02, 0.02, 0.05, 1]
    
    MDBoxLayout:
        orientation: 'vertical'
        padding: "20dp"
        spacing: "20dp"

        MDLabel:
            id: content_label
            text: "اضغط على الزر ليظهر لك حديث أو حكمة تثبت فؤادك"
            halign: "center"
            font_style: "H4"  # خط كبير ومريح
            theme_text_color: "Custom"
            text_color: [1, 1, 1, 1]

        MDLabel:
            id: meanings_label
            text: ""
            halign: "center"
            font_style: "Subtitle1"
            theme_text_color: "Custom"
            text_color: [0.7, 0.7, 0.7, 1]

        MDBoxLayout:
            size_hint_y: None
            height: "60dp"
            spacing: "20dp"
            
            MDIconButton:
                icon: "volume-high"
                user_font_size: "40sp"
                on_release: root.speak_text()

            MDFillRoundFlatButton:
                text: "حديث عشوائي"
                on_release: root.show_random_hadith()

class InspirationScreen(MDScreen):
    def show_random_hadith(self):
        with open('database.json', 'r', encoding='utf-8') as f:
            data = json.load(f)
            hadith = random.choice(data['ahadith'])
            self.ids.content_label.text = hadith['text']
            self.ids.meanings_label.text = hadith['meanings']

    def speak_text(self):
        # هنا سيتم ربط محرك الصوت (Adham Voice)
        print("جاري نطق النص بصوت أدهم...")
