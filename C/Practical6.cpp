#include <iostream>
#include <vector>
#include <string>
#include <map>

using namespace std;

class EmployeeExpertSystem {
private:
    struct EmployeeRecord {
        string name;
        int score;
        string performance;
        string recommendation;
        vector<string> suggestions;
    };
    vector<EmployeeRecord> employeeRecords;

    const vector<pair<string, int>> questions = {
        {"Does the employee meet deadlines", 2},
        {"Does the employee work well in a team", 1},
        {"Does the employee take initiative", 1},
        {"Does the employee have leadership qualities", 2},
        {"Does the employee consistently exceed expectations", 2},
        {"Is the employee punctual", 1},
        {"Does the employee adapt well to new challenges", 1},
        {"Does the employee show strong problem-solving skills", 2},
        {"Does the employee maintain a positive attitude", 1},
        {"Does the employee effectively communicate with colleagues", 1}
    };

    void provideSuggestions(vector<string>& suggestions, const vector<string>& negatives) {
        for (const auto& neg : negatives) {
            if (neg.find("deadlines") != string::npos)
                suggestions.push_back("Attend time management training.");
            else if (neg.find("team") != string::npos)
                suggestions.push_back("Participate in team-building activities.");
            else if (neg.find("leadership") != string::npos)
                suggestions.push_back("Take a leadership development course.");
            else if (neg.find("initiative") != string::npos)
                suggestions.push_back("Be proactive in task ownership.");
            else if (neg.find("problem-solving") != string::npos)
                suggestions.push_back("Improve analytical and logical thinking.");
            else if (neg.find("punctual") != string::npos)
                suggestions.push_back("Work on punctuality and discipline.");
            else if (neg.find("communicate") != string::npos)
                suggestions.push_back("Improve verbal and written communication.");
        }
    }

    string generateRecommendation(int score, const vector<string>& negatives) {
        if (score >= 17)
            return "Recommend for Promotion";
        else if (score >= 13)
            return "Eligible for Bonus/Recognition";
        else if (score <= 9)
            return "Needs Training & Mentoring";
        else
            return "Satisfactory but monitor performance";
    }

public:
    void evaluateEmployee() {
        cout << "\nEnter Employee Name: ";
        string name;
        cin.ignore();
        getline(cin, name);

        cout << "\nPerformance Evaluation (yes/no):" << endl;

        int score = 0;
        vector<string> negative_reasons;
        for (size_t i = 0; i < questions.size(); ++i) {
            string question = questions[i].first;
            int weight = questions[i].second;
        
            string answer;
            cout << question << "? ";
            cin >> answer;
        
            if (answer == "yes" || answer == "YES" || answer == "y" || answer == "Yes") {
                score += weight;
            } else {
                negative_reasons.push_back("Not able to " + question.substr(17));
            }
        }
        
        string performance;
        if (score >= 18) performance = "Excellent";
        else if (score >= 14) performance = "Good";
        else if (score >= 10) performance = "Average";
        else performance = "Poor";

        vector<string> suggestions;
        provideSuggestions(suggestions, negative_reasons);

        string recommendation = generateRecommendation(score, negative_reasons);

        employeeRecords.push_back({name, score, performance, recommendation, suggestions});

        cout << "\nFinal Evaluation: " << performance << endl;
        cout << "Score: " << score << " / 20" << endl;
        cout << "Recommendation: " << recommendation << endl;

        if (!negative_reasons.empty()) {
            cout << "\nAreas for Improvement:" << endl;
            for (const auto& reason : negative_reasons)
                cout << "- " << reason << endl;
        }

        if (!suggestions.empty()) {
            cout << "\nExpert Suggestions:" << endl;
            for (const auto& suggestion : suggestions)
                cout << "* " << suggestion << endl;
        }
    }

    void showEmployeeRecords() {
        if (employeeRecords.empty()) {
            cout << "\nNo employee records available." << endl;
            return;
        }

        cout << "\nEmployee Records:\n" << endl;
        int ex = 0, gd = 0, av = 0, pr = 0;
        for (const auto& record : employeeRecords) {
            cout << "Name: " << record.name
                 << ", Score: " << record.score
                 << ", Performance: " << record.performance
                 << ", Recommendation: " << record.recommendation << endl;

            if (record.performance == "Excellent") ex++;
            else if (record.performance == "Good") gd++;
            else if (record.performance == "Average") av++;
            else pr++;
        }

        cout << "\nSummary Statistics:" << endl;
        cout << "Excellent: " << ex << ", Good: " << gd
             << ", Average: " << av << ", Poor: " << pr << endl;
    }

    void run() {
        while (true) {
            cout << "\nMenu:\n1. Evaluate Employee\n2. Show Records\n3. Exit\nChoice: ";
            int ch;
            cin >> ch;
            switch (ch) {
                case 1: evaluateEmployee(); break;
                case 2: showEmployeeRecords(); break;
                case 3: cout << "Exiting...\n"; return;
                default: cout << "Invalid choice. Try again.\n";
            }
        }
    }
};

int main() {
    EmployeeExpertSystem system;
    system.run();
    return 0;
}