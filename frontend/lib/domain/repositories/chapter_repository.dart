import 'package:bookie/domain/entities/chapter_entity.dart';

abstract class ChapterRepository {
  Future<List<Chapter>> getChaptersByStoryId(int storyId);

  Future<Chapter> createChapter(ChapterForm chapterForm);
}
