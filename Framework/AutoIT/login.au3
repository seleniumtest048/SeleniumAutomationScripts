
WinWaitActive("Authentication Required","","20")
 If WinExists("Authentication Required") Then
 Send("amoeba{TAB}")
 Send("amo2012{Enter}")
 EndIf