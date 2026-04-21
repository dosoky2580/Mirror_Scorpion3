from kivymd.uix.screen import MDScreen
from kivy.lang import Builder
from kivy.core.window import Window
import webbrowser

# تصميم واجهة المستندات والعدسة حسب وصفك
KV_DOCS = '''
<DocumentsScreen>:
    md_bg_color: [0.05, 0.05, 0.05, 1]
    
    MDBoxLayout:
        orientation: 'vertical'
        padding: "15dp"
        spacing: "20dp"

        # زر الدخول للعدسة (كاميرا جوجل)
        MDRaisedButton:
            text: "فتح العدسة (Google Lens)"
            size_hint: 1, 0.1
            md_bg_color: [0.2, 0.2, 0.2, 1]
            on_release: root.open_lens()

        MDLabel:
            text: "ترجمة المستندات"
            halign: "center"
            theme_text_color: "Custom"
            text_color: [1, 1, 1, 1]
            font_style: "H5"
            size_hint_y: None
            height: "40dp"

        # منطقة الرابط والبحث
        MDBoxLayout:
            size_hint_y: None
            height: "60dp"
            spacing: "10dp"
            
            MDTextField:
                id: url_input
                hint_text: "أدخل رابط المستند أو المسار"
                mode: "rectangle"
                line_color_focus: [1, 1, 1, 1]

            MDIconButton:
                icon: "magnify"
                md_bg_color: [0.3, 0.3, 0.3, 1]
                on_release: root.fetch_from_url()

        # زر الفتح من المستعرض
        MDRectangleFlatIconButton:
            icon: "folder-open"
            text: "فتح من المستعرض"
            size_hint: 1, 0.08
            text_color: [1, 1, 1, 1]
            line_color: [1, 1, 1, 1]
            on_release: root.open_file_manager()

        Widget: # فاصل مرن

        # زر الترجمة الكبير
        MDFillRoundFlatButton:
            text: "تــرجــمــة"
            size_hint: 0.8, 0.1
            pos_hint: {"center_x": .5}
            font_size: "24sp"
            md_bg_color: [0.6, 0.1, 0.1, 1]
            on_release: root.start_translation()

class DocumentsScreen(MDScreen):
    def open_lens(self):
        # محاولة فتح تطبيق عدسة جوجل مباشرة
        # لو في أندرويد بنستخدم Intent، هنا بنحاكيها بفتح المتصفح للخدمة
        webbrowser.open("https://lens.google.com")

    def fetch_from_url(self):
        url = self.ids.url_input.text
        print(f"جاري جلب المستند من: {url}")

    def open_file_manager(self):
        print("فتح مستعرض ملفات الجهاز...")
        # هنا هنربط مستعرض ملفات أندرويد في الخطوة الجاية

    def start_translation(self):
        print("بدء عملية الترجمة (3 ثواني تحميل)...")
        # هنا هنضيف شاشة الـ Full Screen والورقة اللي بتسحب من اليمين

