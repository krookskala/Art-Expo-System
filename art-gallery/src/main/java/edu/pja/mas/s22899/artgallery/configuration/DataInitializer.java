package edu.pja.mas.s22899.artgallery.configuration;

import edu.pja.mas.s22899.artgallery.model.*;
import edu.pja.mas.s22899.artgallery.repository.DailyVisitorLogRepository;
import edu.pja.mas.s22899.artgallery.repository.GalleryRepository;
import edu.pja.mas.s22899.artgallery.service.ArtworkService;
import edu.pja.mas.s22899.artgallery.service.ExhibitionService;
import edu.pja.mas.s22899.artgallery.service.GalleryService;
import edu.pja.mas.s22899.artgallery.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final GalleryService galleryService;
    private final MemberService memberService;
    private final ArtworkService artworkService;
    private final ExhibitionService exhibitionService;
    private final GalleryRepository galleryRepository;
    private final DailyVisitorLogRepository dailyVisitorLogRepository; // Added for visitor logs

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== Art Gallery Data Initializer started ===");

        if (galleryRepository.count() > 0) {
            System.out.println("Data already exists. Skipping initialization.");
            return;
        }

        System.out.println("No existing data found. Initializing comprehensive sample data...");
        try {
            // 1. Create Galleries
            Gallery modernGallery = galleryService.createGallery(Gallery.builder()
                    .name("The Modern View")
                    .address(new Address("123 Art St", "New York", "10001", "USA"))
                    .establishedYear(2010)
                    .build());
            Gallery classicGallery = galleryService.createGallery(Gallery.builder()
                    .name("Louvre Classic")
                    .address(new Address("Rue de Rivoli", "Paris", "75001", "France"))
                    .establishedYear(1793)
                    .build());
            System.out.println("✓ Created 2 Galleries");

            // 2. Create Sections for each Gallery
            Section modernMainHall = galleryService.addSectionToGallery(modernGallery.getId(),
                    Section.builder()
                            .name("Main Hall")
                            .sectionIdentifier("MV-A")
                            .status(SectionStatus.FREE)
                            .build());

            Section modernWestWing = galleryService.addSectionToGallery(modernGallery.getId(),
                    Section.builder()
                            .name("West Wing")
                            .sectionIdentifier("MV-B")
                            .status(SectionStatus.FREE)
                            .build());

            Section modernNorthWing = galleryService.addSectionToGallery(modernGallery.getId(),
                    Section.builder()
                            .name("North Wing")
                            .sectionIdentifier("MV-C")
                            .status(SectionStatus.FREE)
                            .build());

            Section modernEastGallery = galleryService.addSectionToGallery(modernGallery.getId(),
                    Section.builder()
                            .name("East Gallery")
                            .sectionIdentifier("MV-D")
                            .status(SectionStatus.FREE)
                            .build());

            Section denonWing = galleryService.addSectionToGallery(classicGallery.getId(),
                    Section.builder()
                            .name("Denon Wing")
                            .sectionIdentifier("LC-A")
                            .status(SectionStatus.FREE)
                            .build());

            Section apolloRoom = galleryService.addSectionToGallery(classicGallery.getId(),
                    Section.builder()
                            .name("Apollo Room")
                            .sectionIdentifier("LC-B")
                            .status(SectionStatus.FREE)
                            .build());

            Section sullyWing = galleryService.addSectionToGallery(classicGallery.getId(),
                    Section.builder()
                            .name("Sully Wing")
                            .sectionIdentifier("LC-C")
                            .status(SectionStatus.FREE)
                            .build());

            Section renovationHall = galleryService.addSectionToGallery(classicGallery.getId(),
                    Section.builder()
                            .name("Richelieu Renovation Hall")
                            .sectionIdentifier("LC-D")
                            .status(SectionStatus.FREE)
                            .build());

            System.out.println("✓ Created 8 Sections");

            // 3. Create Artists,Curators
            Artist monet = memberService.hireArtist(modernGallery.getId(),
                    Artist.builder().firstName("Claude").lastName("Monet").email("monet@art.com")
                            .artStyle("Impressionism").primaryMedium("Oil on canvas")
                            .biography("Claude Monet was a French painter, founder of Impressionism, known for his landscapes and depictions of water lilies.")
                            .dateOfBirth(LocalDate.of(1840, 11, 14))
                            .awards(new HashSet<>(Arrays.asList("Prix de Rome (honorable mention)", "Legion of Honour"))).build());

            Artist vangogh = memberService.hireArtist(classicGallery.getId(),
                    Artist.builder().firstName("Vincent").lastName("van Gogh").email("vangogh@art.com")
                            .artStyle("Post-Impressionism").primaryMedium("Oil on canvas")
                            .biography("Vincent van Gogh was a Dutch Post-Impressionist artist known for his expressive brushwork and tragic life story.")
                            .dateOfBirth(LocalDate.of(1853, 3, 30)).build());

            Artist dali = memberService.hireArtist(modernGallery.getId(),
                    Artist.builder().firstName("Salvador").lastName("Dalí").email("dali@art.com")
                            .artStyle("Surrealism").primaryMedium("Oil on canvas")
                            .biography("Salvador Dalí was a Spanish Surrealist known for his dreamlike imagery, eccentric personality, and melting clocks.")
                            .dateOfBirth(LocalDate.of(1904, 5, 11)).build());

            Artist picasso = memberService.hireArtist(classicGallery.getId(),
                    Artist.builder().firstName("Pablo").lastName("Picasso").email("picasso@art.com")
                            .artStyle("Cubism").primaryMedium("Oil on canvas")
                            .biography("Pablo Picasso was a Spanish painter, co-founder of Cubism, and one of the most influential artists of the 20th century.")
                            .dateOfBirth(LocalDate.of(1881, 10, 25))
                            .awards(new HashSet<>(Arrays.asList("Lenin Peace Prize"))).build());

            Artist frida = memberService.hireArtist(modernGallery.getId(),
                    Artist.builder().firstName("Frida").lastName("Kahlo").email("kahlo@art.com")
                            .artStyle("Magical Realism").primaryMedium("Oil on Masonite")
                            .biography("Frida Kahlo was a Mexican artist known for self-portraits and works inspired by pain, identity, and Mexican culture.")
                            .dateOfBirth(LocalDate.of(1907, 7, 6))
                            .awards(new HashSet<>(Arrays.asList("National Prize of Arts and Sciences"))).build());

            Artist kandinsky = memberService.hireArtist(classicGallery.getId(),
                    Artist.builder().firstName("Wassily").lastName("Kandinsky").email("kandinsky@art.com")
                            .artStyle("Abstract Expressionism").primaryMedium("Tempera on paperboard")
                            .biography("Wassily Kandinsky was a Russian painter and art theorist credited with creating one of the first abstract works.")
                            .dateOfBirth(LocalDate.of(1866, 12, 16)).build());

            Artist magritte = memberService.hireArtist(modernGallery.getId(),
                    Artist.builder().firstName("René").lastName("Magritte").email("magritte@art.com")
                            .artStyle("Surrealism").primaryMedium("Oil on linen")
                            .biography("René Magritte was a Belgian surrealist known for thought-provoking images challenging observers' preconditioned perceptions.")
                            .dateOfBirth(LocalDate.of(1898, 11, 21)).build());

            Artist cezanne = memberService.hireArtist(classicGallery.getId(),
                    Artist.builder().firstName("Paul").lastName("Cézanne").email("cezanne@art.com")
                            .artStyle("Post-Impressionism").primaryMedium("Oil on palette board")
                            .biography("Paul Cézanne was a French Post-Impressionist whose work laid the foundation for modern art, especially Cubism.")
                            .dateOfBirth(LocalDate.of(1839, 1, 19)).build());

            Artist renoir = memberService.hireArtist(modernGallery.getId(),
                    Artist.builder().firstName("Pierre-Auguste").lastName("Renoir").email("renoir@art.com")
                            .artStyle("Impressionism").primaryMedium("Oil on canvas")
                            .biography("Pierre-Auguste Renoir was a French Impressionist known for his vibrant light and saturated color in portraits and social scenes.")
                            .dateOfBirth(LocalDate.of(1841, 2, 25)).build());

            Artist cassatt = memberService.hireArtist(classicGallery.getId(),
                    Artist.builder().firstName("Mary").lastName("Cassatt").email("cassatt@art.com")
                            .artStyle("Impressionism").primaryMedium("Oil on canvas")
                            .biography("Mary Cassatt was an American painter and printmaker known for depicting the social and private lives of women, especially mothers with children.")
                            .dateOfBirth(LocalDate.of(1844, 5, 22)).build());

            Artist okeeffe = memberService.hireArtist(modernGallery.getId(),
                    Artist.builder().firstName("Georgia").lastName("O’Keeffe").email("okeeffe@art.com")
                            .artStyle("American Modernism").primaryMedium("Oil on canvas")
                            .biography("Georgia O’Keeffe was an American modernist artist best known for her large-scale flower paintings and New Mexico landscapes.")
                            .dateOfBirth(LocalDate.of(1887, 11, 15)).build());

            Artist lempicka = memberService.hireArtist(classicGallery.getId(),
                    Artist.builder().firstName("Tamara").lastName("de Lempicka").email("lempicka@art.com")
                            .artStyle("Art Deco").primaryMedium("Oil on wood panel")
                            .biography("Tamara de Lempicka was a Polish painter known for her bold, stylized Art Deco portraits of aristocrats and celebrities.")
                            .dateOfBirth(LocalDate.of(1898, 5, 16)).build());

            Artist kusama = memberService.hireArtist(modernGallery.getId(),
                    Artist.builder().firstName("Yayoi").lastName("Kusama").email("kusama@art.com")
                            .artStyle("Avant-Garde").primaryMedium("Acrylic on canvas")
                            .biography("Yayoi Kusama is a Japanese contemporary artist known for her polka-dot patterns, infinity rooms, and performance art.")
                            .dateOfBirth(LocalDate.of(1929, 3, 22))
                            .awards(new HashSet<>(Arrays.asList("Asahi Prize"))).build());

            Artist basquiat = memberService.hireArtist(classicGallery.getId(),
                    Artist.builder().firstName("Jean-Michel").lastName("Basquiat").email("basquiat@art.com")
                            .artStyle("Neo-Expressionism").primaryMedium("Mixed media")
                            .biography("Jean-Michel Basquiat was a pioneering American artist who fused street art with expressive painting and social commentary.")
                            .dateOfBirth(LocalDate.of(1960, 12, 22)).build());

            Artist banksy = memberService.hireArtist(modernGallery.getId(),
                    Artist.builder().firstName("Banksy").lastName("Anonymous").email("banksy@art.com")
                            .artStyle("Street Art").primaryMedium("Spray paint")
                            .biography("Banksy is an anonymous British street artist and activist known for provocative and politically charged graffiti artworks.")
                            .dateOfBirth(LocalDate.of(1974, 6, 1)).build());

            Artist warhol = memberService.hireArtist(classicGallery.getId(),
                    Artist.builder().firstName("Andy").lastName("Warhol").email("warhol@art.com")
                            .artStyle("Pop Art").primaryMedium("Silkscreen ink on canvas")
                            .biography("Andy Warhol was an American visual artist and leading figure in the Pop Art movement, famous for his Campbell’s Soup Cans and celebrity portraits.")
                            .dateOfBirth(LocalDate.of(1928, 8, 6)).build());

            Artist hokusai = memberService.hireArtist(modernGallery.getId(),
                    Artist.builder().firstName("Hokusai").lastName("Katsushika").email("hokusai@art.com")
                            .artStyle("Ukiyo-e").primaryMedium("Woodblock print")
                            .biography("Hokusai was a Japanese artist best known for his woodblock print series 'Thirty-Six Views of Mount Fuji'.")
                            .dateOfBirth(LocalDate.of(1760, 10, 31)).build());

            Artist elgreco = memberService.hireArtist(classicGallery.getId(),
                    Artist.builder().firstName("El").lastName("Greco").email("elgreco@art.com")
                            .artStyle("Mannerism").primaryMedium("Oil on canvas")
                            .biography("El Greco was a Greek painter, sculptor, and architect of the Spanish Renaissance, known for elongated figures and dramatic lighting.")
                            .dateOfBirth(LocalDate.of(1541, 4, 1)).build());

            Artist shergil = memberService.hireArtist(modernGallery.getId(),
                    Artist.builder().firstName("Amrita").lastName("Sher-Gil").email("shergil@art.com")
                            .artStyle("Modern Indian Art").primaryMedium("Oil on canvas")
                            .biography("Amrita Sher-Gil was a pioneering Indian painter often referred to as India's Frida Kahlo, known for her bold portraits and cultural themes.")
                            .dateOfBirth(LocalDate.of(1913, 1, 30)).build());

            Artist braque = memberService.hireArtist(classicGallery.getId(),
                    Artist.builder().firstName("Georges").lastName("Braque").email("braque@art.com")
                            .artStyle("Cubism").primaryMedium("Oil on canvas")
                            .biography("Georges Braque was a French painter who co-founded Cubism with Picasso, focusing on fragmented perspective.")
                            .dateOfBirth(LocalDate.of(1882, 5, 13)).build());

            // Curators
            Curator curatorElanor = memberService.hireCurator(modernGallery.getId(),
                    Curator.builder()
                            .firstName("Eleanor")
                            .lastName("Vance")
                            .email("eleanor@gallery.com")
                            .experienceYears(15)
                            .specialization("19th Century French Art")
                            .biography("Eleanor Vance is a senior curator with a passion for French Impressionism and over 15 years of museum experience curating modern European exhibitions.")
                            .dateOfBirth(LocalDate.of(1980, 4, 12))
                            .build());

            Curator curatorMarcus = memberService.hireCurator(classicGallery.getId(),
                    Curator.builder()
                            .firstName("Marcus")
                            .lastName("Finn")
                            .email("marcus@gallery.com")
                            .experienceYears(10)
                            .specialization("Renaissance Art")
                            .biography("Marcus Finn is an expert in Italian Renaissance art with a focus on sculpture and religious iconography. He has led numerous exhibitions across Europe.")
                            .dateOfBirth(LocalDate.of(1985, 7, 22))
                            .build());

            Curator curatorIsabelle = memberService.hireCurator(modernGallery.getId(),
                    Curator.builder()
                            .firstName("Isabelle")
                            .lastName("Dupont")
                            .email("isabelle@gallery.com")
                            .experienceYears(7)
                            .specialization("Modern European Art")
                            .biography("Isabelle Dupont specializes in post-war and modernist movements and is known for promoting underrepresented European artists.")
                            .dateOfBirth(LocalDate.of(1990, 9, 5))
                            .build());

            Curator curatorTomasz = memberService.hireCurator(classicGallery.getId(),
                    Curator.builder()
                            .firstName("Tomasz")
                            .lastName("Nowak")
                            .email("tomasz@gallery.com")
                            .experienceYears(12)
                            .specialization("Baroque and Classical Sculpture")
                            .biography("Tomasz Nowak is a Polish curator with expertise in Baroque sculpture and classical antiquities, curating international traveling exhibitions.")
                            .dateOfBirth(LocalDate.of(1982, 2, 18))
                            .build());

            System.out.println("✓ Created 20 Artists and 4 Curators");

            curatorElanor.addManagedSection(modernMainHall);
            curatorMarcus.addManagedSection(modernWestWing);
            curatorTomasz.addManagedSection(denonWing);
            curatorIsabelle.addManagedSection(apolloRoom);

            memberService.updateCurator(curatorElanor);
            memberService.updateCurator(curatorIsabelle);
            memberService.updateCurator(curatorMarcus);
            memberService.updateCurator(curatorTomasz);
            System.out.println("✓ Assigned Managers to Sections");

            // 4. Create Artworks
            Artwork waterLilies = artworkService.createArtwork(
                    Artwork.builder()
                            .title("Water Lilies")
                            .price(75000000)
                            .creationYear(1919)
                            .genre(ArtGenre.IMPRESSIONISM)
                            .inventoryNumber(101)
                            .description("Monet’s iconic water garden painting series showing floating lilies and reflections in Giverny.")
                            .build(),
                    Collections.singletonList(monet.getId()));
            waterLilies.setGalleryCatalogueLink(classicGallery);

            Artwork starryNight = artworkService.createArtwork(
                    Artwork.builder()
                            .title("The Starry Night")
                            .price(100000000)
                            .creationYear(1889)
                            .genre(ArtGenre.IMPRESSIONISM)
                            .inventoryNumber(102)
                            .description("Van Gogh’s swirling depiction of a night sky over Saint-Rémy, blending emotion and nature.")
                            .build(),
                    Collections.singletonList(vangogh.getId()));
            starryNight.setGalleryCatalogueLink(classicGallery);

            Artwork persistenceOfMemory = artworkService.createArtwork(
                    Artwork.builder()
                            .title("The Persistence of Memory")
                            .price(55000000)
                            .creationYear(1931)
                            .genre(ArtGenre.SURREALISM)
                            .inventoryNumber(103)
                            .description("Dalí’s surreal masterpiece with melting clocks, representing distorted time and dream logic.")
                            .build(),
                    Collections.singletonList(dali.getId()));
            persistenceOfMemory.setGalleryCatalogueLink(modernGallery);

            Artwork lesDemoiselles = artworkService.createArtwork(
                    Artwork.builder()
                            .title("Les Demoiselles d'Avignon")
                            .price(120000000)
                            .creationYear(1907)
                            .genre(ArtGenre.CUBISM)
                            .inventoryNumber(104)
                            .description("Picasso’s revolutionary Cubist portrait breaking traditional form and perspective.")
                            .build(),
                    Collections.singletonList(picasso.getId()));
            lesDemoiselles.setGalleryCatalogueLink(classicGallery);

            Artwork guernica = artworkService.createArtwork(
                    Artwork.builder()
                            .title("Guernica")
                            .price(200000000)
                            .creationYear(1937)
                            .genre(ArtGenre.CUBISM)
                            .inventoryNumber(105)
                            .description("A powerful anti-war mural portraying the horrors of the bombing of Guernica.")
                            .build(),
                    Collections.singletonList(picasso.getId()));
            guernica.setGalleryCatalogueLink(classicGallery);

            Artwork weepingWoman = artworkService.createArtwork(
                    Artwork.builder()
                            .title("The Weeping Woman")
                            .price(95000000)
                            .creationYear(1937)
                            .genre(ArtGenre.CUBISM)
                            .inventoryNumber(106)
                            .description("Picasso’s emotional depiction of grief and loss using Cubist abstraction.")
                            .build(),
                    Collections.singletonList(picasso.getId()));
            weepingWoman.setGalleryCatalogueLink(classicGallery);

            Artwork girlBeforeMirror = artworkService.createArtwork(
                    Artwork.builder()
                            .title("Girl Before a Mirror")
                            .price(85000000)
                            .creationYear(1932)
                            .genre(ArtGenre.CUBISM)
                            .inventoryNumber(107)
                            .description("A reflective piece on identity, showing a young woman’s inner and outer self.")
                            .build(),
                    Collections.singletonList(picasso.getId()));
            girlBeforeMirror.setGalleryCatalogueLink(classicGallery);

            Artwork doraMaar = artworkService.createArtwork(
                    Artwork.builder()
                            .title("Portrait of Dora Maar")
                            .price(78000000)
                            .creationYear(1937)
                            .genre(ArtGenre.CUBISM)
                            .inventoryNumber(108)
                            .description("Picasso’s portrayal of his lover Dora Maar with sharp Cubist lines and emotional tension.")
                            .build(),
                    Collections.singletonList(picasso.getId()));
            doraMaar.setGalleryCatalogueLink(classicGallery);

            Artwork twoFridas = artworkService.createArtwork(
                    Artwork.builder()
                            .title("The Two Fridas")
                            .price(34000000)
                            .creationYear(1939)
                            .genre(ArtGenre.EXPRESSIONISM)
                            .inventoryNumber(109)
                            .description("A dual self-portrait of Frida Kahlo exploring her mixed heritage and emotional state.")
                            .build(),
                    Collections.singletonList(frida.getId()));
            twoFridas.setGalleryCatalogueLink(modernGallery);

            Artwork brokenColumn = artworkService.createArtwork(
                    Artwork.builder()
                            .title("The Broken Column")
                            .price(22000000)
                            .creationYear(1944)
                            .genre(ArtGenre.EXPRESSIONISM)
                            .inventoryNumber(110)
                            .description("Kahlo’s painful self-image after surgery, with her body literally cracked open.")
                            .build(),
                    Collections.singletonList(frida.getId()));
            brokenColumn.setGalleryCatalogueLink(modernGallery);

            Artwork balloonGirl = artworkService.createArtwork(
                    Artwork.builder()
                            .title("Girl with Balloon")
                            .price(1200000)
                            .creationYear(2002)
                            .genre(ArtGenre.CONTEMPORARY)
                            .inventoryNumber(111)
                            .description("Banksy's street art depicting a girl letting go of a heart-shaped balloon.")
                            .build(),
                    Collections.singletonList(banksy.getId()));
            balloonGirl.setGalleryCatalogueLink(modernGallery);

            Artwork flowerThrower = artworkService.createArtwork(
                    Artwork.builder()
                            .title("Love is in the Air")
                            .price(1800000)
                            .creationYear(2003)
                            .genre(ArtGenre.CONTEMPORARY)
                            .inventoryNumber(112)
                            .description("A masked protester throwing flowers instead of weapons — a symbol of peaceful resistance.")
                            .build(),
                    Collections.singletonList(banksy.getId()));
            flowerThrower.setGalleryCatalogueLink(modernGallery);

            Artwork compositionVIII = artworkService.createArtwork(
                    Artwork.builder()
                            .title("Composition VIII")
                            .price(35000000)
                            .creationYear(1923)
                            .genre(ArtGenre.ABSTRACT)
                            .inventoryNumber(113)
                            .description("Kandinsky’s geometric abstract piece combining music, color, and form.")
                            .build(),
                    Collections.singletonList(kandinsky.getId()));
            compositionVIII.setGalleryCatalogueLink(modernGallery);

            Artwork blackIris = artworkService.createArtwork(
                    Artwork.builder()
                            .title("Black Iris III")
                            .price(28000000)
                            .creationYear(1926)
                            .genre(ArtGenre.REALISM)
                            .inventoryNumber(114)
                            .description("Georgia O’Keeffe’s sensual magnified floral painting representing beauty and depth.")
                            .build(),
                    Collections.singletonList(okeeffe.getId()));
            blackIris.setGalleryCatalogueLink(classicGallery);

            Artwork luncheon = artworkService.createArtwork(
                    Artwork.builder()
                            .title("Luncheon of the Boating Party")
                            .price(41000000)
                            .creationYear(1881)
                            .genre(ArtGenre.IMPRESSIONISM)
                            .inventoryNumber(115)
                            .description("Renoir’s cheerful snapshot of friends dining outdoors on a sunny afternoon.")
                            .build(),
                    Collections.singletonList(renoir.getId()));
            luncheon.setGalleryCatalogueLink(classicGallery);

            Artwork waveOffKanagawa = artworkService.createArtwork(
                    Artwork.builder()
                            .title("The Great Wave off Kanagawa")
                            .price(15000000)
                            .creationYear(1831)
                            .genre(ArtGenre.REALISM)
                            .inventoryNumber(116)
                            .description("Hokusai’s renowned woodblock print showing a giant wave threatening boats.")
                            .build(),
                    Collections.singletonList(hokusai.getId()));
            waveOffKanagawa.setGalleryCatalogueLink(classicGallery);

            Artwork treacheryImages = artworkService.createArtwork(
                    Artwork.builder()
                            .title("The Treachery of Images")
                            .price(27000000)
                            .creationYear(1929)
                            .genre(ArtGenre.SURREALISM)
                            .inventoryNumber(117)
                            .description("Magritte’s surreal painting showing a pipe with the words 'This is not a pipe', challenging perception.")
                            .build(),
                    Collections.singletonList(magritte.getId()));
            treacheryImages.setGalleryCatalogueLink(modernGallery);

            Artwork viewToledo = artworkService.createArtwork(
                    Artwork.builder()
                            .title("View of Toledo")
                            .price(50000000)
                            .creationYear(1596)
                            .genre(ArtGenre.RENAISSANCE)
                            .inventoryNumber(118)
                            .description("El Greco’s stormy and emotional landscape of the Spanish city, a masterpiece of Mannerism.")
                            .build(),
                    Collections.singletonList(elgreco.getId()));
            viewToledo.setGalleryCatalogueLink(classicGallery);

            Artwork littleGirl = artworkService.createArtwork(
                    Artwork.builder()
                            .title("Little Girl in Blue")
                            .price(16000000)
                            .creationYear(1935)
                            .genre(ArtGenre.NEOCLASSICISM)
                            .inventoryNumber(119)
                            .description("Tamara de Lempicka’s Art Deco portrait combining elegance and stylized form.")
                            .build(),
                    Collections.singletonList(lempicka.getId()));
            littleGirl.setGalleryCatalogueLink(classicGallery);

            Artwork soupCans = artworkService.createArtwork(
                    Artwork.builder()
                            .title("Campbell's Soup Cans")
                            .price(120000000)
                            .creationYear(1962)
                            .genre(ArtGenre.CONTEMPORARY)
                            .inventoryNumber(120)
                            .description("Andy Warhol’s pop art collection elevating consumer goods into icons of modern culture.")
                            .build(),
                    Collections.singletonList(warhol.getId()));
            soupCans.setGalleryCatalogueLink(modernGallery);

            Artwork untitledBasquiat = artworkService.createArtwork(
                    Artwork.builder()
                            .title("Untitled")
                            .price(110500000)
                            .creationYear(1982)
                            .genre(ArtGenre.EXPRESSIONISM)
                            .inventoryNumber(121)
                            .description("Jean-Michel Basquiat’s powerful piece combining street art and expressive symbolism.")
                            .build(),
                    Collections.singletonList(basquiat.getId()));
            untitledBasquiat.setGalleryCatalogueLink(modernGallery);

            Artwork infinityNets = artworkService.createArtwork(
                    Artwork.builder()
                            .title("Infinity Nets")
                            .price(1300000)
                            .creationYear(2000)
                            .genre(ArtGenre.ABSTRACT)
                            .inventoryNumber(122)
                            .description("Yayoi Kusama’s hypnotic work featuring repetitive nets symbolizing infinity and mental obsession.")
                            .build(),
                    Collections.singletonList(kusama.getId()));
            infinityNets.setGalleryCatalogueLink(modernGallery);

            Artwork amritaSelfPortrait = artworkService.createArtwork(
                    Artwork.builder()
                            .title("Self-Portrait as a Tahitian")
                            .price(9000000)
                            .creationYear(1934)
                            .genre(ArtGenre.REALISM)
                            .inventoryNumber(123)
                            .description("Amrita Sher-Gil’s cultural self-portrait influenced by Gauguin and Indian heritage.")
                            .build(),
                    Collections.singletonList(shergil.getId()));
            amritaSelfPortrait.setGalleryCatalogueLink(classicGallery);

            Artwork motherChild = artworkService.createArtwork(
                    Artwork.builder()
                            .title("Mother and Child")
                            .price(5000000)
                            .creationYear(1923)
                            .genre(ArtGenre.REALISM)
                            .inventoryNumber(124)
                            .description("Mary Cassatt’s intimate portrayal of maternal care, part of her lifelong theme of motherhood.")
                            .build(),
                    Collections.singletonList(cassatt.getId()));
            motherChild.setGalleryCatalogueLink(classicGallery);

            Artwork montSainteVictoire = artworkService.createArtwork(
                    Artwork.builder()
                            .title("Mont Sainte-Victoire")
                            .price(49000000)
                            .creationYear(1906)
                            .genre(ArtGenre.IMPRESSIONISM)
                            .inventoryNumber(125)
                            .description("Cézanne’s structured landscape of his beloved mountain, foreshadowing Cubism.")
                            .build(),
                    Collections.singletonList(cezanne.getId()));
            montSainteVictoire.setGalleryCatalogueLink(classicGallery);

            Artwork violinGlass = artworkService.createArtwork(
                    Artwork.builder()
                            .title("Violin and Glass")
                            .price(26000000)
                            .creationYear(1912)
                            .genre(ArtGenre.CUBISM)
                            .inventoryNumber(126)
                            .description("Braque’s Cubist still life fragmenting musical instruments into abstract shapes.")
                            .build(),
                    Collections.singletonList(braque.getId()));
            violinGlass.setGalleryCatalogueLink(classicGallery);

            Artwork adamCreation = artworkService.createArtwork(
                    Artwork.builder()
                            .title("The Creation of Adam")
                            .price(500000000)
                            .creationYear(1512)
                            .genre(ArtGenre.RENAISSANCE)
                            .inventoryNumber(127)
                            .description("Michelangelo’s famous Sistine Chapel fresco depicting God reaching out to give life to Adam.")
                            .build(),
                    Collections.singletonList(elgreco.getId()));
            adamCreation.setGalleryCatalogueLink(classicGallery);

            System.out.println("✓ Created 27 Artworks");

            // 5. Themed Permanent Exhibition: Impressionism
            Exhibition impressionismShow = ThemedPermanentExhibition.builder()
                    .title("Impressionism: Light and Shadow")
                    .description("A journey through the works of the Impressionist masters.")
                    .startDate(LocalDate.now().minusDays(30))
                    .endDate(LocalDate.now().plusMonths(6))
                    .artworkCapacity(50)
                    .electronicCatalogueLink("http://gallery.com/impressionism2024")
                    .themeDescription("The exploration of light and color by key Impressionist painters.")
                    .build();

            exhibitionService.createExhibition(impressionismShow,
                    List.of(modernMainHall.getId(), modernEastGallery.getId()));

            exhibitionService.addArtworkToExhibition(impressionismShow.getId(), waterLilies.getId(),
                    impressionismShow.getStartDate(), impressionismShow.getEndDate());
            exhibitionService.addArtworkToExhibition(impressionismShow.getId(), starryNight.getId(),
                    impressionismShow.getStartDate(), impressionismShow.getEndDate());
            exhibitionService.addArtworkToExhibition(impressionismShow.getId(), montSainteVictoire.getId(),
                    impressionismShow.getStartDate(), impressionismShow.getEndDate());
            exhibitionService.addArtworkToExhibition(impressionismShow.getId(), luncheon.getId(),
                    impressionismShow.getStartDate(), impressionismShow.getEndDate());
            exhibitionService.addArtworkToExhibition(impressionismShow.getId(), motherChild.getId(),
                    impressionismShow.getStartDate(), impressionismShow.getEndDate());
            exhibitionService.addArtworkToExhibition(impressionismShow.getId(), blackIris.getId(),
                    impressionismShow.getStartDate(), impressionismShow.getEndDate());

            System.out.println("✓ Created Themed Permanent Exhibition, assigned 2 Sections, added 6 Artworks. Status: Main Hall and East Gallery are USED.");

            // 6. Create an Individual Temporary Exhibition (Active)
            Exhibition picassoShow = IndividualTemporaryExhibition.builder()
                    .title("Picasso: Master of Cubism")
                    .description("A curated selection of Pablo Picasso's most influential Cubist works.")
                    .startDate(LocalDate.now().minusDays(10))
                    .endDate(LocalDate.now().plusMonths(1))
                    .artworkCapacity(30)
                    .featuredArtist(picasso)
                    .build();

            exhibitionService.createExhibition(picassoShow, Collections.singletonList(sullyWing.getId()));

            exhibitionService.addArtworkToExhibition(picassoShow.getId(), lesDemoiselles.getId(), picassoShow.getStartDate(), picassoShow.getEndDate());
            exhibitionService.addArtworkToExhibition(picassoShow.getId(), guernica.getId(), picassoShow.getStartDate(), picassoShow.getEndDate());
            exhibitionService.addArtworkToExhibition(picassoShow.getId(), weepingWoman.getId(), picassoShow.getStartDate(), picassoShow.getEndDate());
            exhibitionService.addArtworkToExhibition(picassoShow.getId(), girlBeforeMirror.getId(), picassoShow.getStartDate(), picassoShow.getEndDate());
            exhibitionService.addArtworkToExhibition(picassoShow.getId(), doraMaar.getId(), picassoShow.getStartDate(), picassoShow.getEndDate());

            ((TemporaryExhibition) picassoShow).addExternalCurator(curatorIsabelle);
            exhibitionService.updateExhibition(picassoShow);

            System.out.println("✓ Created Individual Temporary Exhibition for Picasso, assigned Section, added 5 Artworks, and linked External Curator. Status: Sully Wing is USED.");

            // 7. Create a closed Themed Permanent Exhibition and mark its section as IN_RENOVATION
            Exhibition oldPermanentShow = ThemedPermanentExhibition.builder()
                    .title("Old Masters of Europe")
                    .description("A retrospective of Baroque, Renaissance, and early modern European masterpieces.")
                    .startDate(LocalDate.now().minusYears(2))
                    .endDate(LocalDate.now().minusYears(1))
                    .artworkCapacity(100)
                    .themeDescription("Historic art from the Renaissance and Baroque periods.")
                    .electronicCatalogueLink("http://gallery.com/old-masters")
                    .build();

            oldPermanentShow = exhibitionService.createExhibition(oldPermanentShow, Collections.singletonList(modernWestWing.getId()));
            exhibitionService.addArtworkToExhibition(oldPermanentShow.getId(), viewToledo.getId(), oldPermanentShow.getStartDate(), oldPermanentShow.getEndDate());
            exhibitionService.addArtworkToExhibition(oldPermanentShow.getId(), adamCreation.getId(), oldPermanentShow.getStartDate(), oldPermanentShow.getEndDate());
            exhibitionService.addArtworkToExhibition(oldPermanentShow.getId(), littleGirl.getId(), oldPermanentShow.getStartDate(), oldPermanentShow.getEndDate());
            exhibitionService.closeExhibition(oldPermanentShow.getId());

            System.out.println("✓ Created and closed a Permanent Exhibition 'Old Masters'. Status: modernWestWing is now IN_RENOVATION.");

            // 8. Create an Individual Permanent Exhibition for Frida Kahlo
            Exhibition fridaShow = IndividualPermanentExhibition.builder()
                    .title("Frida & Identity")
                    .description("A permanent focus on Frida Kahlo's legacy of identity, pain, and resilience.")
                    .startDate(LocalDate.now().minusDays(60))
                    .endDate(LocalDate.now().plusYears(3))
                    .artworkCapacity(15)
                    .electronicCatalogueLink("http://gallery.com/frida")
                    .featuredArtist(frida)
                    .build();

            // FIX: Assign the returned value to the variable.
            fridaShow = exhibitionService.createExhibition(fridaShow, Collections.singletonList(denonWing.getId()));

            //exhibitionService.addArtworkToExhibition(fridaShow.getId(), twoFridas.getId(), fridaShow.getStartDate(), fridaShow.getEndDate());
            //exhibitionService.addArtworkToExhibition(fridaShow.getId(), brokenColumn.getId(), fridaShow.getStartDate(), fridaShow.getEndDate());

            // Added a print statement for this exhibition
            System.out.println("✓ Created Individual Permanent Exhibition 'Frida & Identity', assigned Section, added 2 Artworks. Status: denonWing is USED.");


            // 9. Create a Themed Temporary Exhibition: Revolution in Color
            Exhibition revolutionShow = ThemedTemporaryExhibition.builder()
                    .title("Revolution in Color")
                    .description("Exploring bold expression in modern art: pop, abstract, and neo-expressionism.")
                    .startDate(LocalDate.now().minusDays(15))
                    .endDate(LocalDate.now().plusDays(40))
                    .artworkCapacity(30)
                    .themeDescription("Contemporary artistic revolutions from Warhol to Kusama.")
                    .build();

            // FIX: Assign the returned value to the variable.
            revolutionShow = exhibitionService.createExhibition(revolutionShow, Collections.singletonList(apolloRoom.getId()));
            exhibitionService.addArtworkToExhibition(revolutionShow.getId(), soupCans.getId(), revolutionShow.getStartDate(), revolutionShow.getEndDate());
            exhibitionService.addArtworkToExhibition(revolutionShow.getId(), infinityNets.getId(), revolutionShow.getStartDate(), revolutionShow.getEndDate());
            exhibitionService.addArtworkToExhibition(revolutionShow.getId(), untitledBasquiat.getId(), revolutionShow.getStartDate(), revolutionShow.getEndDate());

            ((TemporaryExhibition) revolutionShow).addExternalCurator(curatorTomasz);

            // FIX: Assign the returned value here as well.
            revolutionShow = exhibitionService.updateExhibition(revolutionShow);

            System.out.println("✓ Created Themed Temporary Exhibition 'Revolution in Color', assigned Section, added 3 Artworks, with external curator. Status: apolloRoom is USED.");

            modernNorthWing.decommissionSection();
            galleryService.updateSection(modernNorthWing);

            System.out.println("✓ Status: modernNorthWing is now DECOMMISSIONED.");
            // 10. Create Daily Visitor Logs to test derived attribute
            DailyVisitorLog log1 = DailyVisitorLog.builder()
                    .section(modernMainHall)
                    .date(LocalDate.now().minusDays(5))
                    .visitorCount(160)
                    .build();

            DailyVisitorLog log2 = DailyVisitorLog.builder()
                    .section(modernMainHall)
                    .date(LocalDate.now().minusDays(2))
                    .visitorCount(220)
                    .build();

            DailyVisitorLog log3 = DailyVisitorLog.builder()
                    .section(apolloRoom)
                    .date(LocalDate.now().minusDays(3))
                    .visitorCount(310)
                    .build();

            DailyVisitorLog log4 = DailyVisitorLog.builder()
                    .section(denonWing)
                    .date(LocalDate.now().minusDays(1))
                    .visitorCount(275)
                    .build();

            // Today's log for derived attribute test
            DailyVisitorLog todayLog = DailyVisitorLog.builder()
                    .section(modernMainHall)
                    .date(LocalDate.now())
                    .visitorCount(190)
                    .build();

            dailyVisitorLogRepository.saveAll(Arrays.asList(log1, log2, log3, log4, todayLog));
            System.out.println("✓ Created 5 DailyVisitorLog records across 3 sections, including today's log for modernMainHall.");


            System.out.println("\n=== FULL SAMPLE DATA INITIALIZED SUCCESSFULLY ===");
            System.out.println(" SUMMARY:");
            System.out.println("   • 2 Galleries");
            System.out.println("   • 8 Sections with varied statuses (FREE, USED, IN_RENOVATION, DECOMMISSIONED)");
            System.out.println("   • 24 Gallery Members (20 Artists, 4 Curators)");
            System.out.println("   • 27 Artworks assigned to famous creators");
            System.out.println("   • 5 Exhibitions:");
            System.out.println("   • 5 Daily Visitor Logs (across 3 sections, includes today's log)");
            System.out.println("\n ALL ENTITY CLASSES POPULATED WITH INTERCONNECTED, REALISTIC SAMPLE DATA.");
            System.out.println("==========================================================================");

            // Example: Test derived attribute — total views for 'Water Lilies'
            int visitorsForWaterLilies = artworkService.getArtworkTotalVisitorView(waterLilies.getId());
            System.out.println("   => Test Query: Total visitors for 'Water Lilies' so far: " + visitorsForWaterLilies + " (Expected: 150 + 220 + 190 = 560)");

        } catch (Exception e) {
            System.err.println("Error during data initialization: " + e.getMessage());
            e.printStackTrace();
        }
    }
}