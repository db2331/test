import tkinter as tk
from tkinter import scrolledtext
import webbrowser
import random
import re
from sentence_transformers import SentenceTransformer, util

# Load model
model = SentenceTransformer('all-MiniLM-L6-v2')

# Intents
intents = {
    "greeting": {
        "patterns": ["Hi", "Hello", "Hey there", "Heyyaa", "What's up?", "Yo", "Howdy", "Hola", "Greetings", "Good morning", "Good evening"],
        "responses": [
            "Hi! How can I assist you today?",
            "Hello there! Need help with something?",
            "Hey! What can I do for you?",
            "Yo! How can I help?",
            "Greetings! How may I be of service?"
        ]
    },
    "order_status": {
        "patterns": ["Where is my order?", "Track my order", "Order status please", "Where's my stuff?", "Is my order here yet?", "Check delivery status"],
        "responses": [
            "Please share your order ID so I can check it for you.",
            "Sure! What’s your order ID?",
            "Let me know your order number to help you track it.",
            "I can help with that! Just send me your order ID."
        ]
    },
    "cancel_order": {
        "patterns": ["Cancel my order", "I want to cancel", "Can I cancel this order?", "I changed my mind", "How do I cancel the order?", "Stop my order"],
        "responses": [
            "To cancel, go to 'My Orders' and choose 'Cancel Order'.",
            "Yes, you can cancel if it hasn't been shipped yet.",
            "Just head to your orders page and select 'Cancel'.",
            "Order not shipped? You can cancel from the order page easily."
        ]
    },
    "return_policy": {
        "patterns": ["How to return?", "Return policy?", "Can I return my item?", "What is the return policy?", "How do I return an item?", "Return this product"],
        "responses": [
            "You can return within 30 days of delivery.",
            "Check your order details to initiate a return.",
            "Returns are accepted within 30 days — make sure the item is unused!",
            "Unhappy with the product? You can return it easily within 30 days."
        ]
    },
    "payment_methods": {
        "patterns": ["How can I pay?", "Available payment methods?", "Do you accept credit cards?", "Can I pay with UPI?", "What are the payment options?"],
        "responses": [
            "We accept credit/debit cards, UPI, net banking, and wallets.",
            "You can pay using UPI, cards, net banking, or popular e-wallets.",
            "Yes, we accept a wide range of payment methods including UPI and cards."
        ]
    },
    "thanks": {
        "patterns": ["Thanks", "Thank you", "Much appreciated", "Thanks a lot", "Thanks for your help"],
        "responses": [
            "You're most welcome!",
            "Happy to help!",
            "Glad I could assist!",
            "Anytime! Let me know if you need anything else."
        ]
    },
    "goodbye": {
        "patterns": ["Bye", "Goodbye", "See you later", "Take care", "Catch you later"],
        "responses": [
            "Thanks for visiting! Hope to see you again soon!",
            "Goodbye! Let us know if you need anything else.",
            "Take care! Come back anytime.",
            "Have a great day! 😊"
        ]
    }
}

# Pattern preparation
all_patterns = []
tags = []
for tag, data in intents.items():
    for pattern in data["patterns"]:
        all_patterns.append(pattern)
        tags.append(tag)

pattern_embeddings = model.encode(all_patterns, convert_to_tensor=True)

# Context
context = {
    "expecting_order_id": False,
    "last_intent": None
}

# Text preprocessing
def preprocess(text):
    return re.sub(r"[^\w\s]", "", text.lower()).strip()

# URL opener
def open_url(url):
    webbrowser.open_new(url)

# Response generator
def get_response(user_input):
    clean_input = preprocess(user_input)

    if context["expecting_order_id"]:
        context["expecting_order_id"] = False
        return f"You can check the status of your order (ID: {clean_input}) here:\n👉 Click to track: https://www.example.com/myorders/{clean_input}"

    user_embedding = model.encode(clean_input, convert_to_tensor=True)
    similarities = util.cos_sim(user_embedding, pattern_embeddings)[0]
    best_match = similarities.argmax().item()
    confidence = similarities[best_match].item()

    if confidence < 0.7:
        return "Sorry, I didn't quite get that. Could you please rephrase your request?"

    matched_tag = tags[best_match]

    if matched_tag == "order_status":
        context["expecting_order_id"] = True

    context["last_intent"] = matched_tag
    return random.choice(intents[matched_tag]["responses"])

# Send message handler
def send_message():
    user_input = entry.get()
    if user_input.strip() == "":
        return

    chat_area.config(state=tk.NORMAL)
    chat_area.insert(tk.END, f"You: {user_input}\n", "user")
    entry.delete(0, tk.END)

    response = get_response(user_input)

    if "https://www.example.com/myorders/" in response:
        parts = response.split("https://www.example.com/myorders/")
        base_text = parts[0]
        order_id = parts[1].strip()
        full_url = f"https://www.example.com/myorders/{order_id}"

        chat_area.insert(tk.END, f"Bot: {base_text}", "bot")
        start_idx = chat_area.index(tk.END)
        chat_area.insert(tk.END, full_url + "\n\n", "link")
        chat_area.tag_add("link", start_idx, f"{start_idx} lineend")
        chat_area.tag_config("link", foreground="blue", underline=True)
        chat_area.tag_bind("link", "<Button-1>", lambda e: open_url(full_url))
    else:
        chat_area.insert(tk.END, f"Bot: {response}\n\n", "bot")

    chat_area.config(state=tk.DISABLED)
    chat_area.yview(tk.END)

# ------------------- GUI ---------------------

window = tk.Tk()
window.title("E-commerce Chatbot")
window.geometry("600x500")
window.configure(bg="#f2f2f2")

header = tk.Label(window, text="🛒 E-commerce Chat Assistant", bg="#4a90e2", fg="white",
                  font=("Segoe UI", 16, "bold"), pady=10)
header.pack(fill=tk.X)

chat_area = scrolledtext.ScrolledText(window, wrap=tk.WORD, state=tk.DISABLED,
                                      font=("Segoe UI", 11), bg="#ffffff", padx=10, pady=10, height=20)
chat_area.pack(padx=10, pady=(10, 5), fill=tk.BOTH)
chat_area.tag_config("user", foreground="#3333cc", font=("Segoe UI", 11, "bold"))
chat_area.tag_config("bot", foreground="#009933", font=("Segoe UI", 11))

entry_frame = tk.Frame(window, bg="#f2f2f2")
entry_frame.pack(padx=10, pady=10, fill=tk.X)

entry = tk.Entry(entry_frame, font=("Segoe UI", 12), relief=tk.GROOVE, bd=2)
entry.pack(side=tk.LEFT, fill=tk.X, expand=True, padx=(0, 10))
entry.bind("<Return>", lambda event: send_message())

send_button = tk.Button(entry_frame, text="Send", command=send_message,
                        font=("Segoe UI", 10, "bold"), bg="#4CAF50", fg="white", activebackground="#45a049",
                        relief=tk.FLAT, padx=20, pady=5, cursor="hand2")
send_button.pack(side=tk.RIGHT)

chat_area.config(state=tk.NORMAL)
chat_area.insert(tk.END, "Bot: Hello! How can I help you today?\n\n", "bot")
chat_area.config(state=tk.DISABLED)

window.mainloop()