package com.example.demo.entities;

import lombok.Getter;
import lombok.Setter;
import org.apache.lucene.analysis.cjk.CJKWidthFilterFactory;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.StopFilterFactory;
import org.apache.lucene.analysis.ja.*;
import org.hibernate.search.annotations.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Indexed
@Getter
@Setter
@Table(name = "demofts.book")
@AnalyzerDef(name = "customanalyzer",
tokenizer = @TokenizerDef(factory = JapaneseTokenizerFactory.class),
filters = {
		@TokenFilterDef(factory = JapaneseBaseFormFilterFactory.class),
		@TokenFilterDef(factory = JapanesePartOfSpeechStopFilterFactory.class),
		@TokenFilterDef(factory = CJKWidthFilterFactory.class),
		@TokenFilterDef(factory = StopFilterFactory.class),
		@TokenFilterDef(factory = JapaneseKatakanaStemFilterFactory.class),
		@TokenFilterDef(factory = JapaneseReadingFormFilterFactory.class),
		@TokenFilterDef(factory = LowerCaseFilterFactory.class)
})
public class Book {
    @Id
    @Column(name = "book_id")
    private Integer bookId;

    @Column(name = "title", nullable= false, length = 128, columnDefinition = "nvarchar(128)")
    @Field(index= Index.YES, analyze= Analyze.YES, store= Store.NO, termVector = TermVector.YES)
    @Analyzer(definition = "customanalyzer")
    private String title;

    @Column(name = "description", nullable= false, length = 256, columnDefinition = "nvarchar(256)")
    @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO, termVector = TermVector.YES)
    @Analyzer(definition = "customanalyzer")
    private String description;

    @Column(name = "author", nullable= false, length = 64, columnDefinition = "nvarchar(64)")
    @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO, termVector = TermVector.YES)
    @Analyzer(definition = "customanalyzer")
    private String author;

    // Getter and Setter
    //....
}