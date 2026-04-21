from kivymd.uix.screen import MDScreen
from kivy.lang import Builder
from kivy.properties import StringProperty

KV_DIALOGUE = '''
<DialogueScreen>:
    md_bg_color: [0.05, 0.05, 0.05, 1]
    
    MDBoxLayout:
        orientation: 'vertical'
        padding: "15dp"
        spacing: "10dp"

        # المربع العلوي (اللغة الأصلية)
        MDTextField:
            id: top_editor
            hint_text: "الكلام الملتقط باللغة الأولى"
            multiline: True
            mode: "fill"
            fill_color_normal: [0.1, 0.1, 0.1, 1]
            size_hint_y: 0.35

        # المربع السفلي (لغة الترجمة)
        MDBoxLayout:
            orientation: 'vertical'
            size_hint_y: 0.35
            canvas.before:
                Color:
                    rgba: [0.15, 0.15, 0.15, 1]
                RoundedRectangle:
                    pos: self.pos
                    size: self.size
                    radius: [10,]
            
            MDLabel:
                id: bottom_translated
                text: "الترجمة تظهر هنا..."
                halign: "center"
                theme_text_color: "Custom"
                text_color: [1, 1, 1, 1]
            
            MDIconButton:
                icon: "volume-high"
                pos_hint: {"right": 1}
                on_release: root.speak_translation()

        # منطقة التحكم (اللغات، التبديل، المايك)
        MDBoxLayout:
            size_hint_y: 0.2
            spacing: "5dp"
            adaptive_height: True

            # زر اختيار اللغة اليمين
            MDRaisedButton:
                id: lang_right
                text: "العربية"
                size_hint_x: 0.3
                on_release: root.select_language('right')

            # زر التبديل
            MDIconButton:
                icon: "swap-horizontal"
                theme_icon_color: "Custom"
                icon_color: [1, 1, 1, 1]
                on_release: root.swap_languages()

            # المايك (بحجم جيد)
            MDFloatingActionButton:
                icon: "microphone"
                md_bg_color: [0.6, 0.1, 0.1, 1] # أحمر سكربيون
                icon_size: "35sp"
                on_release: root.start_listening()

            # زر اختيار اللغة اليسار
            MDRaisedButton:
                id: lang_left
                text: "التركية"
                size_hint_x: 0.3
                on_release: root.select_language('left')

class DialogueScreen(MDScreen):
    def swap_languages(self):
        # تبديل النصوص بين الزر اليمين واليسار
        right_text = self.ids.lang_right.text
        self.ids.lang_right.text = self.ids.lang_left.text
        self.ids.lang_left.text = right_text
        print("تم تبديل اللغات")

    def start_listening(self):
        # هنا يتم المسح لبدء جملة جديدة كما طلبت
        self.ids.top_editor.text = ""
        self.ids.bottom_translated.text = "جاري الاستماع..."
        print(f"يلتقط الكلام بناءً على لغة الزر اليمين: {self.ids.lang_right.text}")
        # سيتم ربط مكتبة التعرف على الصوت لاحقاً

    def speak_translation(self):
        print("نطق الترجمة بصوت أدهم...")

    def select_language(self, side):
        print(f"فتح قائمة الـ 100 لغة للجانب الـ {side}")
