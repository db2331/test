import random
import re
from sentence_transformers import SentenceTransformer, util

# Load pre-trained BERT model
model = SentenceTransformer('all-MiniLM-L6-v2')

# Intent dictionary for scalability
intents = {
    "greeting": {
        "patterns": ["Hi", "Hello", "Hey there", "Heyyaa", "What's up?", "Yo", "Howdy", "Hola"],
        "responses": [
            "Hi! How can I assist you today?",
            "Hello there! Need help with something?",
            "Hey! What can I do for you?",
            "Yo! How can I help?"
        ]
    },
    "order_status": {
        "patterns": ["Where is my order?", "Track my order", "Order status please", "Where's my stuff?", "Is my order here yet?"],
        "responses": [
            "Please share your order ID so I can check it for you.",
            "Sure! What’s your order ID?",
            "Let me know your order number to help you track it."
        ]
    },
    "cancel_order": {
        "patterns": ["Cancel my order", "I want to cancel", "Can I cancel this order?", "I changed my mind", "How do I cancel the order?"],
        "responses": [
            "To cancel, go to 'My Orders' and choose 'Cancel Order'.",
            "Yes, you can cancel if it hasn't been shipped yet.",
            "Just head to your orders page and select 'Cancel'."
        ]
    },
    "return_policy": {
        "patterns": ["How to return?", "Return policy?", "Can I return my item?", "What is the return policy?", "How do I return an item?"],
        "responses": [
            "You can return within 30 days of delivery.",
            "Check your order details to initiate a return.",
            "Returns are accepted within 30 days — make sure the item is unused!"
        ]
    },
    "thanks": {
        "patterns": ["Thanks", "Thank you", "Much appreciated", "Thanks a lot", "Thanks for your help"],
        "responses": [
            "You're most welcome!",
            "Happy to help!",
            "Glad I could assist!"
        ]
    },
    "goodbye": {
        "patterns": ["Bye", "Goodbye", "See you later", "Take care", "Catch you later"],
        "responses": [
            "Thanks for visiting! Hope to see you again soon!",
            "Goodbye! Let us know if you need anything else.",
            "Take care! Come back anytime."
        ]
    }
}

# Flatten patterns and tags
all_patterns = []
tags = []
for tag, data in intents.items():
    for pattern in data["patterns"]:
        all_patterns.append(pattern)
        tags.append(tag)

# Encode all patterns once
pattern_embeddings = model.encode(all_patterns, convert_to_tensor=True)

# Context tracker
context = {
    "expecting_order_id": False,
    "last_intent": None  # Track last matched intent for better context management
}

# Clean and normalize text
def preprocess(text):
    return re.sub(r"[^\w\s]", "", text.lower()).strip()

# Get response
def get_response(user_input):
    clean_input = preprocess(user_input)

    # Handle expected order ID
    if context["expecting_order_id"]:
        context["expecting_order_id"] = False
        return f"You can check the status of your order (ID: {clean_input}) by visiting your Orders Page:\n👉 https://www.example.com/myorders"

    # Match user input to known patterns
    user_embedding = model.encode(clean_input, convert_to_tensor=True)
    similarities = util.cos_sim(user_embedding, pattern_embeddings)[0]
    best_match = similarities.argmax().item()
    confidence = similarities[best_match].item()

    if confidence < 0.7:  # Increased confidence threshold
        return "Sorry, I didn't quite get that. Could you please rephrase your request?"

    matched_tag = tags[best_match]

    # If asking for order status, expect an ID next
    if matched_tag == "order_status":
        context["expecting_order_id"] = True

    # Remember last matched intent for context
    context["last_intent"] = matched_tag

    # Randomize response for variety
    return random.choice(intents[matched_tag]["responses"])

# Chat loop
print("E-commerce Bot: Hello! How can I help you today? (Type 'quit' to exit)")

while True:
    user_input = input("You: ")
    
    if user_input.lower() == "quit":
        print("E-commerce Bot: Goodbye! Have a great day.")
        break
    
    bot_reply = get_response(user_input)
    print("E-commerce Bot:", bot_reply)
