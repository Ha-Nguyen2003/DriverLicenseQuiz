USE [master]
GO
IF EXISTS (SELECT * FROM sys.databases WHERE name = 'quizSytem')
	DROP DATABASE quizSytem
GO
/****** Object:  Database [quizSytem]    Script Date: 8/21/2024 3:24:14 AM ******/
CREATE DATABASE [quizSytem]
GO
USE [quizSytem]
GO
/****** Object:  Table [dbo].[Answers]    Script Date: 8/21/2024 3:24:14 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Answers](
	[AnswerID] [int] IDENTITY(1,1) NOT NULL,
	[QuestionID] [int] NULL,
	[AnswerText] [nvarchar](500) NOT NULL,
	[IsCorrect] [bit] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[AnswerID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Questions]    Script Date: 8/21/2024 3:24:14 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Questions](
	[QuestionID] [int] IDENTITY(1,1) NOT NULL,
	[QuizID] [int] NULL,
	[QuestionText] [nvarchar](500) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[QuestionID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Quizzes]    Script Date: 8/21/2024 3:24:14 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Quizzes](
	[QuizID] [int] IDENTITY(1,1) NOT NULL,
	[TeacherID] [int] NULL,
	[QuizName] [nvarchar](100) NOT NULL,
	[status] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[QuizID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Results]    Script Date: 8/21/2024 3:24:14 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Results](
	[ResultID] [int] IDENTITY(1,1) NOT NULL,
	[UserID] [int] NULL,
	[QuizID] [int] NULL,
	[Score] [float] NULL,
	[CreatedAt] [date] NULL,
PRIMARY KEY CLUSTERED 
(
	[ResultID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Users]    Script Date: 8/21/2024 3:24:14 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Users](
	[UserID] [int] IDENTITY(1,1) NOT NULL,
	[Name] [nvarchar](50) NOT NULL,
	[Email] [nvarchar](50) NOT NULL,
	[Password] [nvarchar](50) NOT NULL,
	[Role] [nvarchar](20) NOT NULL,
	[Status] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[UserID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[Answers] ON 

INSERT [dbo].[Answers] ([AnswerID], [QuestionID], [AnswerText], [IsCorrect]) VALUES (1, 1, N'Paris', 1)
INSERT [dbo].[Answers] ([AnswerID], [QuestionID], [AnswerText], [IsCorrect]) VALUES (2, 1, N'London', 0)
INSERT [dbo].[Answers] ([AnswerID], [QuestionID], [AnswerText], [IsCorrect]) VALUES (3, 1, N'Berlin', 0)
INSERT [dbo].[Answers] ([AnswerID], [QuestionID], [AnswerText], [IsCorrect]) VALUES (4, 2, N'Shakespeare', 1)
INSERT [dbo].[Answers] ([AnswerID], [QuestionID], [AnswerText], [IsCorrect]) VALUES (5, 2, N'Dickens', 0)
INSERT [dbo].[Answers] ([AnswerID], [QuestionID], [AnswerText], [IsCorrect]) VALUES (6, 2, N'Hemingway', 0)
INSERT [dbo].[Answers] ([AnswerID], [QuestionID], [AnswerText], [IsCorrect]) VALUES (7, 3, N'Mice', 1)
INSERT [dbo].[Answers] ([AnswerID], [QuestionID], [AnswerText], [IsCorrect]) VALUES (8, 3, N'Mouses', 0)
INSERT [dbo].[Answers] ([AnswerID], [QuestionID], [AnswerText], [IsCorrect]) VALUES (9, 3, N'Meece', 0)
INSERT [dbo].[Answers] ([AnswerID], [QuestionID], [AnswerText], [IsCorrect]) VALUES (10, 4, N'czxcz', 0)
INSERT [dbo].[Answers] ([AnswerID], [QuestionID], [AnswerText], [IsCorrect]) VALUES (11, 4, N'czxcxz', 1)
INSERT [dbo].[Answers] ([AnswerID], [QuestionID], [AnswerText], [IsCorrect]) VALUES (12, 4, N'czxczx', 0)
INSERT [dbo].[Answers] ([AnswerID], [QuestionID], [AnswerText], [IsCorrect]) VALUES (13, 4, N'czxczx', 0)
INSERT [dbo].[Answers] ([AnswerID], [QuestionID], [AnswerText], [IsCorrect]) VALUES (14, 5, N'123', 1)
INSERT [dbo].[Answers] ([AnswerID], [QuestionID], [AnswerText], [IsCorrect]) VALUES (15, 5, N'123', 0)
INSERT [dbo].[Answers] ([AnswerID], [QuestionID], [AnswerText], [IsCorrect]) VALUES (16, 5, N'123', 0)
INSERT [dbo].[Answers] ([AnswerID], [QuestionID], [AnswerText], [IsCorrect]) VALUES (17, 5, N'123', 0)
SET IDENTITY_INSERT [dbo].[Answers] OFF
GO
SET IDENTITY_INSERT [dbo].[Questions] ON 

INSERT [dbo].[Questions] ([QuestionID], [QuizID], [QuestionText]) VALUES (1, 1, N'What is the capital of France?')
INSERT [dbo].[Questions] ([QuestionID], [QuizID], [QuestionText]) VALUES (2, 1, N'Who wrote Romeo and Juliet?')
INSERT [dbo].[Questions] ([QuestionID], [QuizID], [QuestionText]) VALUES (3, 2, N'What is the plural of "mouse"?')
INSERT [dbo].[Questions] ([QuestionID], [QuizID], [QuestionText]) VALUES (4, 3, N'  Phần của đường bộ được sử dụng cho các phương tiện giao thông qua lại là gì?')
INSERT [dbo].[Questions] ([QuestionID], [QuizID], [QuestionText]) VALUES (5, 3, N'“Làn đường” là gì?')
SET IDENTITY_INSERT [dbo].[Questions] OFF
GO
SET IDENTITY_INSERT [dbo].[Quizzes] ON 

INSERT [dbo].[Quizzes] ([QuizID], [TeacherID], [QuizName], [status]) VALUES (1, 1, N'Đề số 1', 1)
INSERT [dbo].[Quizzes] ([QuizID], [TeacherID], [QuizName], [status]) VALUES (2, 1, N'Đề số 2', 1)
INSERT [dbo].[Quizzes] ([QuizID], [TeacherID], [QuizName], [status]) VALUES (3, 1, N'Đề số 3', 1)
SET IDENTITY_INSERT [dbo].[Quizzes] OFF
GO
SET IDENTITY_INSERT [dbo].[Results] ON 

INSERT [dbo].[Results] ([ResultID], [UserID], [QuizID], [Score], [CreatedAt]) VALUES (1, 2, 1, 8, CAST(N'2024-08-21' AS Date))
INSERT [dbo].[Results] ([ResultID], [UserID], [QuizID], [Score], [CreatedAt]) VALUES (2, 3, 1, 5, CAST(N'2024-08-21' AS Date))
INSERT [dbo].[Results] ([ResultID], [UserID], [QuizID], [Score], [CreatedAt]) VALUES (3, 3, 2, 9, CAST(N'2024-08-21' AS Date))
INSERT [dbo].[Results] ([ResultID], [UserID], [QuizID], [Score], [CreatedAt]) VALUES (4, 4, 3, 0, CAST(N'2024-08-21' AS Date))
SET IDENTITY_INSERT [dbo].[Results] OFF
GO
SET IDENTITY_INSERT [dbo].[Users] ON 

INSERT [dbo].[Users] ([UserID], [Name], [Email], [Password], [Role], [Status]) VALUES (1, N'Teacher', N'Teacher@gmail.com', N'password1', N'teacher', 1)
INSERT [dbo].[Users] ([UserID], [Name], [Email], [Password], [Role], [Status]) VALUES (2, N'Student1', N'Student1@gmail.com', N'password1', N'student', 0)
INSERT [dbo].[Users] ([UserID], [Name], [Email], [Password], [Role], [Status]) VALUES (3, N'Student2', N'Student3@gmail.com', N'password2', N'student', 0)
INSERT [dbo].[Users] ([UserID], [Name], [Email], [Password], [Role], [Status]) VALUES (4, N'test sign up', N'testsignup01@gmail.com', N'123', N'student', 1)
INSERT [dbo].[Users] ([UserID], [Name], [Email], [Password], [Role], [Status]) VALUES (5, N'czxcxz', N'testsignup02@gmail.com', N'123', N'student', 1)
SET IDENTITY_INSERT [dbo].[Users] OFF
GO
ALTER TABLE [dbo].[Results] ADD  DEFAULT (getdate()) FOR [CreatedAt]
GO
ALTER TABLE [dbo].[Answers]  WITH CHECK ADD  CONSTRAINT [FK_Answers_Questions] FOREIGN KEY([QuestionID])
REFERENCES [dbo].[Questions] ([QuestionID])
GO
ALTER TABLE [dbo].[Answers] CHECK CONSTRAINT [FK_Answers_Questions]
GO
ALTER TABLE [dbo].[Questions]  WITH CHECK ADD  CONSTRAINT [FK_Questions_Quizzes] FOREIGN KEY([QuizID])
REFERENCES [dbo].[Quizzes] ([QuizID])
GO
ALTER TABLE [dbo].[Questions] CHECK CONSTRAINT [FK_Questions_Quizzes]
GO
ALTER TABLE [dbo].[Quizzes]  WITH CHECK ADD  CONSTRAINT [FK_Quizzes_Users] FOREIGN KEY([TeacherID])
REFERENCES [dbo].[Users] ([UserID])
GO
ALTER TABLE [dbo].[Quizzes] CHECK CONSTRAINT [FK_Quizzes_Users]
GO
ALTER TABLE [dbo].[Results]  WITH CHECK ADD  CONSTRAINT [FK_Results_Quizzes] FOREIGN KEY([QuizID])
REFERENCES [dbo].[Quizzes] ([QuizID])
GO
ALTER TABLE [dbo].[Results] CHECK CONSTRAINT [FK_Results_Quizzes]
GO
ALTER TABLE [dbo].[Results]  WITH CHECK ADD  CONSTRAINT [FK_Results_Users] FOREIGN KEY([UserID])
REFERENCES [dbo].[Users] ([UserID])
GO
ALTER TABLE [dbo].[Results] CHECK CONSTRAINT [FK_Results_Users]
GO
USE [master]
GO
ALTER DATABASE [quizSytem] SET  READ_WRITE 
GO
