object MockJson {
    val chatMock = """
        [
   {
       "step": "step_1",
       "type": "button",
       "content": {
           "text": "Merhaba, canlı destek hattına hoş geldiniz! Hangi konuda yardım almak istersiniz?",
           "buttons": [
               {
                   "label": "İade işlemi",
                   "action": "step_2"
               },
               {
                   "label": "Sipariş durumu",
                   "action": "step_3"
               },
               {
                   "label": "Ürün rehberi",
                   "action": "step_4"
               },
               {
                   "label": "Sohbeti bitir",
                   "action": "end_conversation"
               }
           ]
       },
       "action": "await_user_choice"
   },
   {
       "step": "step_2",
       "type": "button",
       "content": {
           "text": "İade işlemleri için ürününüzü kargoya verdiniz mi?",
           "buttons": [
               {
                   "label": "Evet, kargoya verdim",
                   "action": "step_5"
               },
               {
                   "label": "Hayır, henüz vermedim",
                   "action": "step_6"
               },
               {
                   "label": "Sohbeti bitir",
                   "action": "end_conversation"
               }
           ]
       },
       "action": "await_user_choice"
   },
   {
       "step": "step_3",
       "type": "text",
       "content": "Siparişiniz şu anda kargoda. Takip numaranız: TR123456789",
       "action": "end_conversation"
   },
   {
       "step": "step_4",
       "type": "image",
       "content": "https://www.bigjoy.com.tr/image/secmerehberi/screhberi_mbl.jpg",
       "action": "show_guide"
   },
   {
       "step": "step_5",
       "type": "text",
       "content": "Teşekkür ederiz! İade işleminiz kargoya ulaştığında işleme alınacaktır.",
       "action": "end_conversation"
   },
   {
       "step": "step_6",
       "type": "text",
       "content": "İade işlemi için ürünü kargoya verdikten sonra işlemler başlatılacaktır. Yardıma ihtiyacınız olursa bizimle iletişime geçebilirsiniz.",
       "action": "end_conversation"
   },
   {
       "step": "step_7",
       "type": "button",
       "content": {
           "text": "Başka nasıl yardımcı olabilirim?",
           "buttons": [
               {
                   "label": "Yeni bir işlem başlat",
                   "action": "step_1"
               },
               {
                   "label": "Sohbeti bitir",
                   "action": "end_conversation"
               }
           ]
       },
       "action": "await_user_choice"
   }
]
    """
}