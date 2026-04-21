from kivy.animation import Animation
from kivymd.uix.screen import MDScreen
from kivy.lang import Builder
from kivy.properties import ObjectProperty, StringProperty
from kivy.clock import ScheduleOnce

KV_TRANS = '''
<TranslationView>:
    # شاشة المستند الأصلي
    MDBoxLayout:
        id: original_doc
        md_bg_color: [1, 1, 1, 1] # خلفية بيضاء كأنه ورق
        MDLabel:
            text: "هنا يظهر المستند الأصلي... (اضغط ضغطاً مطولاً للمعاينة)"
            halign: "center"
            theme_text_color: "Custom"
            text_color: [0, 0, 0, 1]

    # الورقة المترجمة (التي تسحب من اليمين)
    MDBoxLayout:
        id: translated_paper
        x: root.width  # تبدأ خارج الشاشة من اليمين
        md_bg_color: [0.95, 0.95, 0.95, 1]
        canvas.after:
            # التوقيع المائل 130 درجة (شفاف)
            Color:
                rgba: [0.5, 0.5, 0.5, 0.2]
            PushMatrix
            Rotate:
                angle: 130
                origin: self.center
            Rectangle:
                texture: self.get_watermark_texture()
                size: [self.width, 50]
                pos: [self.center_x - self.width/2, self.center_y]
            PopMatrix

        MDLabel:
            text: "تمت الترجمة بواسطة ميرور سكربيون"
            halign: "center"
            font_style: "H6"

    # دائرة التحميل (3 ثواني)
    MDSpinner:
        id: loader
        size_hint: None, None
        size: "46dp"
        pos_hint: {'center_x': .5, 'center_y': .5}
        active: True

class TranslationView(MDScreen):
    def on_enter(self):
        # بدء عداد التحميل 3 ثواني
        ScheduleOnce(self.reveal_translation, 3)

    def reveal_translation(self, dt):
        # إيقاف التحميل وتحريك الورقة من اليمين
        self.ids.loader.active = False
        anim = Animation(x=0, duration=1, t='out_quad')
        anim.start(self.ids.translated_paper)

    def on_touch_down(self, touch):
        # إذا ضغط المستخدم دوسه طويله (أو حتى ضغطة عادية للمعاينة)
        if self.collide_point(*touch.pos):
            # إخفاء المترجم لإظهار الأصلي
            self.ids.translated_paper.opacity = 0
            return True
        return super().on_touch_down(touch)

    def on_touch_up(self, touch):
        # عند رفع الإصبع يظهر المترجم مرة أخرى
        self.ids.translated_paper.opacity = 1
        return super().on_touch_up(touch)

    def get_watermark_texture(self):
        # دالة وهمية لإنشاء نص التوقيع المائل
        from kivy.uix.label import Label
        l = Label(text="Mirror Scorpion - Translated", font_size=20)
        l.texture_update()
        return l.texture
